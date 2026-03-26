// Package model defines the domain models used across the application.
package model

// Department represents a business unit or organisational division.
// It contains basic information such as its unique identifier, name, and description.
type Department struct {
	// ID is the unique identifier of the department.
	ID int `json:"id"`

	// Name is the name of the department.
	Name string `json:"name"`

	// Description provides additional details about the department.
	Description string `json:"description"`
}
