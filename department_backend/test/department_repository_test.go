// Package test contiene las pruebas unitarias y de integración para validar
// el comportamiento de repositorios y servicios.
package test

import (
	"database/sql"
	"log"
	"testing"

	"github.com/AppBlitz/department_backend/internal/database/mysqls"
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
)

type Connections struct {
	Connection *sql.DB
}

func ReadyConnection() *Connections {
	database, er := mysqls.ConnectionDatabaseMysql()
	if er != nil {
		log.Fatal("Erro in connection", er)
	}
	return &Connections{Connection: database}
}

func (db *Connections) TestCRUD(t *testing.T) {
	t.Run("save_department", func(t *testing.T) {
		models := &model.Department{ID: 12345678, Name: "Software", Description: "es un departamento de desarrollo de software de la empresa"}
		repos := repository.NewDepartmentRepository(db.Connection)
		err := repos.Save(models)
		if err != nil {
			t.Fatal(err)
		}
	})
	t.Run("search_department", func(t *testing.T) {
		repo := repository.NewDepartmentRepository(db.Connection)
		_, err := repo.FindByID(9870)
		if err != nil {
			t.Fatal(err)
		}
	})
	t.Run("receive_all_department", func(t *testing.T) {
		repo := repository.NewDepartmentRepository(db.Connection)
		_, err := repo.FindAll()
		if err != nil {
			t.Fatal(err)
		}
	})
}
