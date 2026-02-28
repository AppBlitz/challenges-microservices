// Package repository
package repository

import (
	"database/sql"
	"fmt"
	"log"

	"github.com/AppBlitz/department_backend/internal/model"
)

type DepartmentRepository interface {
	Save(department *model.Department) error
	FindByID(id int64) (*model.Department, error)
	FindAll() ([]*model.Department, error)
}

type mysqlDepartmentRepo struct {
	db *sql.DB
}

func NewDepartmentRepository(db *sql.DB) DepartmentRepository {
	return &mysqlDepartmentRepo{db: db}
}

func (r *mysqlDepartmentRepo) Save(dept *model.Department) error {
	query := "INSERT INTO departments (id,name,description) VALUES (?,?,?)"
	_, err := r.db.Exec(query, &dept.ID, &dept.Name, &dept.Description)
	if err != nil {
		return err
	}
	if err != nil {
		return err
	}
	return nil
}

func (r *mysqlDepartmentRepo) FindByID(id int64) (*model.Department, error) {
	department := &model.Department{}
	query := "select depart.id,depart.name,depart.description from departments depart where depart.id=?"
	rows := r.db.QueryRow(query, id)
	if err := rows.Scan(&department.ID, &department.Name, &department.Description); err != nil {
		if err == sql.ErrNoRows {
			return department, fmt.Errorf("%s %d %w", "Department not found with id", id, err)
		}
		return department, fmt.Errorf("department %d: %v", id, err)
	}
	return department, nil
}

func (r *mysqlDepartmentRepo) FindAll() ([]*model.Department, error) {
	departments := []*model.Department{}
	query := "select depart.id,depart.name,depart.description from departments depart"
	rows, err := r.db.Query(query)
	defer func() {
		if err = rows.Close(); err != nil {
			log.Print(err)
		}
	}()
	for rows.Next() {
		department := &model.Department{}
		if err = rows.Scan(&department.ID, &department.Name, &department.Description); err != nil {
			return nil, fmt.Errorf("%s %w", "erro in search departments", err)
		}
		departments = append(departments, department)
	}
	if err != nil {
		return nil, err
	}
	return departments, nil
}
