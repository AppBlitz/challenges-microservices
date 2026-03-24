import Log from "../model/log_model.js"
import { save_employee_save_logs, search_all_logs } from "../repositories/mongo_db.js"

function insert_log_save_employee(log_employee_save) {
  const employee_log_save = new Log(log_employee_save.ID_employee, log_employee_save.name_employee, log_employee_save.email_employee, log_employee_save.department_id, log_employee_save.date_enter)
  save_employee_save_logs(employee_log_save)
}

function search_log() {
  return search_all_logs;
}

export { insert_log_save_employee, search_log }

