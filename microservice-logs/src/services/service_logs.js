import { add_logs_save_employee } from "../repositories/cassandra.js"

function insert_log_save_employee(log_employee_save) {
  const employee_log_save = {
    ID_employee: log_employee_save.ID_employee,
    name_employee: log_employee_save.name_employee,
    email_employee: log_employee_save.email_employee,
    department_id: log_employee_save.department_id,
    date_enter: log_employee_save.date_enter
  }
  add_logs_save_employee(JSON.stringify(employee_log_save));
}
export { insert_log_save_employee }
