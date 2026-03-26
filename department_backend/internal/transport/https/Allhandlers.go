package https

import (
	"net/http"

	_ "github.com/AppBlitz/department_backend/docs"

	httpSwagger "github.com/swaggo/http-swagger"
)

func AllHandlers(han *DepartmentHandler) *http.ServeMux {
	mux := http.NewServeMux()
	mux.HandleFunc("/department/save/", han.SaveDepartments)
	mux.HandleFunc("/department/search/{id}/", han.DepartmentID)
	mux.HandleFunc("/department/all/", han.FindAllDepartments)
	mux.HandleFunc("/swagger/", httpSwagger.WrapHandler)
	return mux
}
