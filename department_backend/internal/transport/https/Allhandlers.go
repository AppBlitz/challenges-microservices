// Package https provides the HTTP transport layer and routing logic
// for the department management service.
package https

import (
	"net/http"

	// The blank import is used here to initialize the swagger documentation package.
	// _ "github.com/AppBlitz/department_backend/docs"
	_ "github.com/AppBlitz/department_backend/docs"
	httpSwagger "github.com/swaggo/http-swagger"
)

// AllHandlers initializes a new HTTP request multiplexer (ServeMux) and registers
// all the routes associated with department operations and API documentation.
func AllHandlers(han *DepartmentHandler) *http.ServeMux {
	// Create a new request multiplexer
	mux := http.NewServeMux()

	// Register route for saving a new department
	mux.HandleFunc("/department/save/", han.SaveDepartments)

	// Register route for searching a department by its ID
	mux.HandleFunc("/department/search/{id}/", han.DepartmentID)

	// Register route for retrieving all department records
	mux.HandleFunc("/department/all/", han.FindAllDepartments)

	// Register route for the Swagger UI documentation
	mux.HandleFunc("/swagger/", httpSwagger.WrapHandler)

	return mux
}
