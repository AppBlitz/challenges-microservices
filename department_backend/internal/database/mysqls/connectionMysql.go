// Package mysqls provides utilities for establishing and managing
// connections to a MySQL database using environment variables.
package mysqls

import (
	"database/sql"
	"fmt"
	"os"
	"time"

	"github.com/go-sql-driver/mysql"
)

// configConnectionDatabaseMysql populates a MySQL configuration structure
// using values retrieved from the system's environment variables.
func configConnectionDatabaseMysql() *mysql.Config {
	cfg := mysql.NewConfig()
	cfg.User = os.Getenv("MYSQL_USER")
	cfg.Passwd = os.Getenv("MYSQL_PASSWORD")
	cfg.Net = os.Getenv("MYSQL_DBNET")
	cfg.Addr = os.Getenv("MYSQL_DBADDR")
	cfg.DBName = os.Getenv("MYSQL_DATABASE")
	return cfg
}

// configHandleDatabaseMysql creates a database handle (sql.DB) by
// formatting a Data Source Name (DSN) from the configuration.
func configHandleDatabaseMysql() (sqls *sql.DB, err error) {
	cfg := configConnectionDatabaseMysql()

	sqls, err = sql.Open("mysql", cfg.FormatDSN())
	if err != nil {
		return nil, err
	}
	return sqls, nil
}

// ConnectionDatabaseMysql attempts to connect to the MySQL database.
// It includes a retry mechanism that pings the database up to 10 times
// before returning an error and sets connection pool limits upon success.
func ConnectionDatabaseMysql() (*sql.DB, error) {
	db, err := configHandleDatabaseMysql()
	if err != nil {
		return nil, err
	}

	maxRetries := 10
	// This loop uses the integer range feature (Go 1.22+) to attempt connection retries
	for range maxRetries {
		// Attempting to connect silently
		if err := db.Ping(); err == nil {
			// Successful connection established
			db.SetMaxOpenConns(4)
			db.SetMaxIdleConns(2)
			return db, nil
		}

		// Wait for 2 seconds before the next attempt
		time.Sleep(2 * time.Second)
	}

	return nil, fmt.Errorf("no se pudo conectar a MySQL tras %d intentos", maxRetries)
}
