// Package test contains automated tests to verify the functionality
// of the application's services and repositories.
package test

import (
	"database/sql"
	"testing"

	"github.com/AppBlitz/department_backend/internal/database/mysqls"
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
	"github.com/AppBlitz/department_backend/internal/service"
)

// connectOrSkip abre la conexión a MySQL y verifica con Ping() que realmente
// está disponible. Si no lo está, saltea el test en lugar de hacer panic.
// sql.Open() nunca falla — la conexión real ocurre en el primer query,
// por eso el Ping() es necesario.
func connectOrSkip(t *testing.T) *sql.DB {
	t.Helper()

	db, err := mysqls.ConnectionDatabaseMysql()
	if err != nil || db == nil {
		t.Skip("MySQL no disponible: no se pudo crear el cliente")
	}

	if err := db.Ping(); err != nil {
		t.Skipf("MySQL no disponible: ping falló (%v)", err)
	}

	t.Cleanup(func() { db.Close() })
	return db
}

// TestServiceGetIDdepartment verifies that the SearchDepartmentID method
// successfully retrieves a department by its identifier.
func TestServiceGetIDdepartment(t *testing.T) {
	db := connectOrSkip(t)
	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)

	_, err := servi.SearchDepartmentID(1234)
	if err != nil {
		t.Errorf("SearchDepartmentID(1234) retornó error inesperado: %v", err)
	}
}

// TestAllDepartments verifies that the FinAllDepartments method
// successfully retrieves all department records from the database.
func TestAllDepartments(t *testing.T) {
	db := connectOrSkip(t)
	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)

	result, err := servi.FinAllDepartments()
	if err != nil {
		t.Errorf("FinAllDepartments() retornó error inesperado: %v", err)
	}
	if result == nil {
		t.Error("FinAllDepartments() retornó nil, se esperaba una lista")
	}
}

// TestSaveDepartmentOfService verifies that a new department model
// can be correctly persisted to the database through the service layer.
func TestSaveDepartmentOfService(t *testing.T) {
	db := connectOrSkip(t)
	repos := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repos)

	dept := &model.Department{
		ID:          12345678,
		Name:        "Software",
		Description: "es un departamento de desarrollo de software de la empresa",
	}

	err := servi.SaveDepartment(dept)
	if err != nil {
		t.Errorf("SaveDepartment() retornó error inesperado: %v", err)
	}
}
