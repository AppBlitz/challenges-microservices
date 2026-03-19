import { Client } from "cassandra-driver"
const host_cassandra = process.env.host_cassandra
const query_save_employee = "INSERT INTO register_logs (ID_employee,name_employee,email_employee,department_id,date_enter) VALUES(?,?,?,?,?)";
const query_log_delete_employee = "";
const query_search_all_notifications_ID_employee = "SELECT * FROM  register_logs WHERE ID_employee = ? ";
const client = new Client({
  contactPoints: [host_cassandra],
  localDataCenter: "datacenter1",
  keyspace: "logs_registre_employees"
})

function add_logs_save_employee(log_employee_save) {
  client.execute(query_save_employee, [
    log_employee_save.ID_employee,
    log_employee_save.name_employee,
    log_employee_save.email_employee,
    log_employee_save.department_id,
    log_employee_save.date_enter
  ], { prepare: true })
}

function add_log_delete_employee(log_delete_employee) {
  client.execute(query_log_delete_employee, [log_delete_employee], { prepare: true });
}

function search_for_id_employee(id_employee) {
  notifications = client.execute(query_search_all_notifications_ID_employee, [id_employee], { prepare: true })
}

export { add_logs_save_employee, add_log_delete_employee, search_for_id_employee };
