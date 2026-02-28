package https

import (
	"net/http"
)

func AllHandlers(han *DepartmentHandler) *http.ServeMux {
	mux := http.NewServeMux()
	mux.HandleFunc("/department/save/", han.SaveDepartments)
	mux.HandleFunc("/department/search/{id}/", han.DepartmentID)
	mux.HandleFunc("/department/all/", han.FindAllDepartments)
	return mux
}
