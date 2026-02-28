// Package app
package app

import (
	"database/sql"
	"log"
	"net/http"

	"github.com/AppBlitz/department_backend/internal/repository"
	"github.com/AppBlitz/department_backend/internal/service"
	"github.com/AppBlitz/department_backend/internal/transport/https"
)

func Run(db *sql.DB) {
	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)
	trans := https.NewDepartmentHandler(servi)
	erro := http.ListenAndServe(":8080", https.AllHandlers(trans))
	if erro != nil {
		log.Fatal("Erro in server: ", erro)
	}
}
