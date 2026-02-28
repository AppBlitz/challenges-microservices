package main

import (
	"log"

	"github.com/AppBlitz/department_backend/internal/app"
	"github.com/AppBlitz/department_backend/internal/database/mysqls"
)

func main() {
	db, err := mysqls.ConnectionDatabaseMysql()
	if err != nil {
		log.Fatal(err)
	} else {
		defer func() {
			err := db.Close()
			if err != nil {
				log.Fatal(err)
			}
		}()
		app.Run(db)
	}
}
