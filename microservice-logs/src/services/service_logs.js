import { save_employee_save_logs, search_all_logs, save_employee_delete_logs, search_all_delete_logs } from "../repositories/mongo_db.js"

function insert_log_save_employee(log_employee_save) {
  save_employee_save_logs(log_employee_save)
}

// NUEVA FUNCIÓN
function insert_log_delete_employee(log_employee_delete) {
  save_employee_delete_logs(log_employee_delete)
}

function search_log() {
  return search_all_logs();
}

// NUEVA FUNCIÓN
function search_delete_log() {
  return search_all_delete_logs();
}

export { insert_log_save_employee, search_log, insert_log_delete_employee, search_delete_log }