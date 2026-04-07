// Package test contiene las pruebas unitarias y de integración para validar
// el comportamiento de repositorios y servicios.
package test

import (
	"testing"

	"github.com/AppBlitz/department_backend/internal/database/mysqls"
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
)

// TestGetDepartmentID valida que se pueda recuperar un departamento por ID.
func TestGetDepartmentID(t *testing.T) {
	db, _ := mysqls.ConnectionDatabaseMysql()
	repo := repository.NewDepartmentRepository(db)
	_, err := repo.FindByID(1234)
	if err != nil {
		t.Error(err)
	}
}

// TestFindAllDepartments valida la recuperación de la lista completa de departamentos.
func TestFindAllDepartments(t *testing.T) {
	db, _ := mysqls.ConnectionDatabaseMysql()
	repo := repository.NewDepartmentRepository(db)
	_, err := repo.FindAll()
	if err != nil {
		t.Error(err)
	}
}

// Test_save_department valida que el guardado de un nuevo departamento sea exitoso.
func Test_save_department(t *testing.T) {
	models := &model.Department{ID: 12345678, Name: "Software", Description: "es un departamento de desarrollo de software de la empresa"}
	db, _ := mysqls.ConnectionDatabaseMysql()
	repos := repository.NewDepartmentRepository(db)
	err := repos.Save(models)
	if err != nil {
		t.Error(err)
	}
}
