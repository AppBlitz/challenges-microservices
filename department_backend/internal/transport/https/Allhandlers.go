package https

import (
	"encoding/json"
	"net/http"

	_ "github.com/AppBlitz/department_backend/docs"
	"github.com/prometheus/client_golang/prometheus/promhttp"
	httpSwagger "github.com/swaggo/http-swagger"
)

func AllHandlers(han *DepartmentHandler) *http.ServeMux {
	mux := http.NewServeMux()

	mux.HandleFunc("/department/save/", han.SaveDepartments)
	mux.HandleFunc("/department/search/{id}/", han.DepartmentID)
	mux.HandleFunc("/department/all/", han.FindAllDepartments)
	mux.HandleFunc("/swagger/", httpSwagger.WrapHandler)

	mux.Handle("/metrics", promhttp.Handler())
	mux.HandleFunc("/health", func(w http.ResponseWriter, r *http.Request) {
		w.Header().Set("Content-Type", "application/json")
		w.WriteHeader(http.StatusOK)
		json.NewEncoder(w).Encode(map[string]interface{}{
			"status":  "UP",
			"service": "service-departments",
			"checks": map[string]string{
				"database": "UP",
			},
		})
	})

	return mux
}