// Package test contains automated tests to verify the functionality
// of the application's services and repositories.
package test

import (
	"testing"

	"github.com/AppBlitz/department_backend/internal/database/mysqls"
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
	"github.com/AppBlitz/department_backend/internal/service"
)

var db, _ = mysqls.ConnectionDatabaseMysql()

// TestServiceGetIDdepartment verifies that the SearchDepartmentID method
// successfully retrieves a department by its identifier.
func TestServiceGetIDdepartment(t *testing.T) {
	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)
	_, err := servi.SearchDepartmentID(1234)
	if err != nil {
		t.Error(err)
	}
}

// TestAllDepartments verifies that the FinAllDepartments method
// successfully retrieves all department records from the database.
func TestAllDepartments(t *testing.T) {
	db, _ := mysqls.ConnectionDatabaseMysql()
	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)
	_, err := servi.FinAllDepartments()
	if err != nil {
		t.Error(err)
	}
}

// TestSaveDepartmentOfService verifies that a new department model
// can be correctly persisted to the database through the service layer.
func TestSaveDepartmentOfService(t *testing.T) {
	models := &model.Department{ID: 12345678, Name: "Software", Description: "es un departamento de desarrollo de software de la empresa"}
	db, _ := mysqls.ConnectionDatabaseMysql()
	repos := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repos)
	err := servi.SaveDepartment(models)
	if err != nil {
		t.Error(err)
	}
}
