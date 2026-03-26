// Package repository provides implementations for accessing
// and managing department data in a persistent storage.
package repository

import (
	"database/sql"
	"fmt"
	"log"

	"github.com/AppBlitz/department_backend/internal/model"
)

// DepartmentRepository defines the behavior required to
// manage department entities in a data source.
type DepartmentRepository interface {
	// Save persists a new department into the data source.
	// Returns an error if the operation fails.
	Save(department *model.Department) error

	// FindByID retrieves a department by its unique identifier.
	// Returns the department if found, otherwise an error.
	FindByID(id int64) (*model.Department, error)

	// FindAll retrieves all departments from the data source.
	// Returns a slice of departments or an error if the operation fails.
	FindAll() ([]*model.Department, error)
}

// mysqlDepartmentRepo is a MySQL implementation of the DepartmentRepository interface.
type mysqlDepartmentRepo struct {
	db *sql.DB
}

// NewDepartmentRepository creates a new instance of DepartmentRepository
// using a MySQL database connection.
func NewDepartmentRepository(db *sql.DB) DepartmentRepository {
	return &mysqlDepartmentRepo{db: db}
}

// Save inserts a new department record into the database.
// It returns an error if the execution of the query fails.
func (r *mysqlDepartmentRepo) Save(dept *model.Department) error {
	query := "INSERT INTO departments (id,name,description) VALUES (?,?,?)"
	_, err := r.db.Exec(query, &dept.ID, &dept.Name, &dept.Description)
	if err != nil {
		return err
	}
	return nil
}

// FindByID searches for a department by its ID.
// If the department does not exist, it returns a descriptive error.
func (r *mysqlDepartmentRepo) FindByID(id int64) (*model.Department, error) {
	department := &model.Department{}
	query := "select depart.id,depart.name,depart.description from departments depart where depart.id=?"

	row := r.db.QueryRow(query, id)
	if err := row.Scan(&department.ID, &department.Name, &department.Description); err != nil {
		if err == sql.ErrNoRows {
			return department, fmt.Errorf("department not found with id %d: %w", id, err)
		}
		return department, fmt.Errorf("error retrieving department %d: %v", id, err)
	}

	return department, nil
}

// FindAll retrieves all departments from the database.
// It iterates over the result set and maps each row into a Department struct.
// Returns an error if any issue occurs during query execution or scanning.
func (r *mysqlDepartmentRepo) FindAll() ([]*model.Department, error) {
	departments := []*model.Department{}
	query := "select depart.id,depart.name,depart.description from departments depart"

	rows, err := r.db.Query(query)
	if err != nil {
		return nil, err
	}
	defer func() {
		if err = rows.Close(); err != nil {
			log.Print(err)
		}
	}()

	for rows.Next() {
		department := &model.Department{}
		if err = rows.Scan(&department.ID, &department.Name, &department.Description); err != nil {
			return nil, fmt.Errorf("error searching departments: %w", err)
		}
		departments = append(departments, department)
	}

	if err = rows.Err(); err != nil {
		return nil, err
	}

	return departments, nil
}
