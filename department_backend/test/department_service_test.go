package test

import (
	"testing"

	"github.com/AppBlitz/department_backend/internal/database/mysqls"
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
	"github.com/AppBlitz/department_backend/internal/service"
)

func TestServiceGetIDdepartment(t *testing.T) {
	db, _ := mysqls.ConnectionDatabaseMysql()
	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)
	_, err := servi.SearchDepartmentID(1234)
	if err != nil {
		t.Error(err)
	}
}

func TestAllDepartments(t *testing.T) {
	db, _ := mysqls.ConnectionDatabaseMysql()
	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)
	_, err := servi.FinAllDepartments()
	if err != nil {
		t.Error(err)
	}
}

func TestSaveDepartmentOfService(t *testing.T) {
	models := &model.Department{ID: "124568", Name: "Software", Description: "es un departamento de desarrollo de software de la empresa"}
	db, _ := mysqls.ConnectionDatabaseMysql()
	repos := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repos)
	err := servi.SaveDepartment(models)
	if err != nil {
		t.Error(err)
	}
}
