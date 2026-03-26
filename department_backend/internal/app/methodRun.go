// Package app handles the initialization and startup of the application's
// core components and the HTTP server.
package app

import (
	"database/sql"
	"log"
	"net/http"
	"os"

	"github.com/AppBlitz/department_backend/internal/repository"
	"github.com/AppBlitz/department_backend/internal/service"
	"github.com/AppBlitz/department_backend/internal/transport/https"
)

// Run initializes the dependency injection chain (repository -> service -> handler)
// and starts the HTTP server on the port specified by the environment variable.
func Run(db *sql.DB) {
	// Initialize the data access layer (Repository)
	repo := repository.NewDepartmentRepository(db)
	// Initialize the business logic layer (Service)
	servi := service.NewDepartmentService(repo)
	// Initialize the network/transport layer (Handler)
	trans := https.NewDepartmentHandler(servi)

	// Retrieve the listening port from environment variables
	portListen := os.Getenv("PORT_LISTEN")

	// Start the HTTP server with the configured handlers
	erro := http.ListenAndServe(":"+portListen, https.AllHandlers(trans))
	if erro != nil {
		// Log a fatal error and exit if the server fails to start
		log.Fatal("Erro in server: ", erro)
	}
}
