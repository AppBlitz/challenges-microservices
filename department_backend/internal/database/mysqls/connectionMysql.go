// Package myslqs is
package mysqls

import (
	"database/sql"
	"fmt"
	"os"
	"time"

	"github.com/go-sql-driver/mysql"
)

func configConnectionDatabaseMysql() *mysql.Config {
	cfg := mysql.NewConfig()
	cfg.User = os.Getenv("MYSQL_USER")
	cfg.Passwd = os.Getenv("MYSQL_PASSWORD")
	cfg.Net = os.Getenv("MYSQL_DBNET")
	cfg.Addr = os.Getenv("MYSQL_DBADDR")
	cfg.DBName = os.Getenv("MYSQL_DATABASE")
	return cfg
}

func configHandleDatabaseMysql() (sqls *sql.DB, err error) {
	cfg := configConnectionDatabaseMysql()

	sqls, err = sql.Open("mysql", cfg.FormatDSN())
	if err != nil {
		return nil, err
	}
	return sqls, nil
}

func ConnectionDatabaseMysql() (*sql.DB, error) {
	db, err := configHandleDatabaseMysql()
	if err != nil {
		return nil, err
	}

	maxRetries := 10
	for range maxRetries {
		// Intentamos conectar silenciosamente
		if err := db.Ping(); err == nil {
			// Conexión exitosa
			db.SetMaxOpenConns(4)
			db.SetMaxIdleConns(2)
			return db, nil
		}

		time.Sleep(2 * time.Second)
	}

	return nil, fmt.Errorf("no se pudo conectar a MySQL tras %d intentos", maxRetries)
}
