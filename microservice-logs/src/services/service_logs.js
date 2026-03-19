import { add_logs } from "../repositories/cassandra.js"

function insert_log_save_employee(log_employee_save) {

  const employee_log_save = {
    ID_employee: log_employee_save.ID_employee,
    name_employee: log_employee_save.name_employee,
    email_employee: log_employee_save.email_employee,
    department_id: log_employee_save.department_id,
    date_enter: log_employee_save.date_enter
  }
  add_logs(employee_log_save, self.crypto.randomUUID());
}
export { insert_log_save_employee }
