// Package service handles the business logic for department-related operations.
package service

import (
	"github.com/AppBlitz/department_backend/internal/model"
	"github.com/AppBlitz/department_backend/internal/repository"
)

// DepartmentService provides methods to interact with department data
// by coordinating between the transport layer and the repository.
type DepartmentService struct {
	repos repository.DepartmentRepository
}

// NewDepartmentService initializes and returns a new pointer to DepartmentService
// using the provided repository implementation.
func NewDepartmentService(r repository.DepartmentRepository) *DepartmentService {
	return &DepartmentService{repos: r}
}

// SearchDepartmentID searches for a single department in the repository
// using its unique ID and returns the result or an error.
func (departS *DepartmentService) SearchDepartmentID(id int64) (*model.Department, error) {
	depart, err := departS.repos.FindByID(id)
	if err != nil {
		return nil, err
	}
	return depart, nil
}

// FinAllDepartments retrieves all department records from the data source
// and returns them as a slice of department pointers.
func (departS *DepartmentService) FinAllDepartments() ([]*model.Department, error) {
	departmens, err := departS.repos.FindAll()
	if err != nil {
		return nil, err
	}
	return departmens, nil
}

// SaveDepartment takes a department model and sends it to the repository
// layer to be stored in the database.
func (departS *DepartmentService) SaveDepartment(department *model.Department) error {
	erro := departS.repos.Save(department)
	if erro != nil {
		return erro
	}
	return nil
}
