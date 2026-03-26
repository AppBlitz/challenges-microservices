// Package configs provides configuration settings and utilities
// for the application's network and environment setup.
package configs

import "net/http"

// EnableCors sets the necessary HTTP headers to allow Cross-Origin Resource Sharing.
// It allows any origin to access the resources and specifies the permitted
// HTTP methods and headers for the requests.
func EnableCors(w http.ResponseWriter) {
	// Allows any domain to access the resource (using the "*" wildcard)
	w.Header().Set("Access-Control-Allow-Origin", "*")
	// Defines which HTTP methods are permitted when accessing the resource
	w.Header().Set("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS")
	// Specifies which headers can be used during the actual request
	w.Header().Set("Access-Control-Allow-Headers", "Content-Type, Authorization")
}
