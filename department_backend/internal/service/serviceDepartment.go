// Package service
package service

import (
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
)

type DepartmentService struct {
	repos repository.DepartmentRepository
}

func NewDepartmentService(r repository.DepartmentRepository) *DepartmentService {
	return &DepartmentService{repos: r}
}

func (departS *DepartmentService) SearchDepartmentID(id int64) (*model.Department, error) {
	depart, err := departS.repos.FindByID(id)
	if err != nil {
		return nil, err
	}
	return depart, nil
}

func (departS *DepartmentService) FinAllDepartments() ([]*model.Department, error) {
	departmens, err := departS.repos.FindAll()
	if err != nil {
		return nil, err
	}
	return departmens, nil
}

func (departS *DepartmentService) SaveDepartment(department *model.Department) error {
	erro := departS.repos.Save(department)
	if erro != nil {
		return erro
	}
	return nil
}
