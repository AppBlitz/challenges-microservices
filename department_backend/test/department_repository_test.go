package test

import (
	"testing"

	"github.com/AppBlitz/department_backend/internal/database/mysqls"
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
)

func TestGetDepartmentID(t *testing.T) {
	db, _ := mysqls.ConnectionDatabaseMysql()
	repo := repository.NewDepartmentRepository(db)
	_, err := repo.FindByID(1234)
	if err != nil {
		t.Error(err)
	}
}

func TestFindAllDepartments(t *testing.T) {
	db, _ := mysqls.ConnectionDatabaseMysql()
	repo := repository.NewDepartmentRepository(db)
	_, err := repo.FindAll()
	if err != nil {
		t.Error(err)
	}
}

func Test_save_department(t *testing.T) {
	models := &model.Department{ID: "124579", Name: "Software", Description: "es un departamento de desarrollo de software de la empresa"}
	db, _ := mysqls.ConnectionDatabaseMysql()
	repos := repository.NewDepartmentRepository(db)
	err := repos.Save(models)
	if err != nil {
		t.Error(err)
	}
}
