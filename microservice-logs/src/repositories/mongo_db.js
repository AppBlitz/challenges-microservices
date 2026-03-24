import mongoose from "mongoose"
const user_mongo = process.env.MONGO_INITDB_ROOT_USERNAME
const user_password = process.env.MONGO_INITDB_ROOT_PASSWORD
const name_database = process.env.MONGO_INITDB_DATABASE
const host_database = process.env.RABBITMQ_HOST
const uri = `mongodb://${user_mongo}:${user_password}@${host_database}/${name_database}?authSource=admin`
const { Schema, connect } = mongoose
connect(uri)
const schema_logs = new Schema(
  {
    id_employee: Number,
    name_user: String,
    user_email: String,
    department_id: Number,
    date_enter_user: Date
  }
)
const save_log_employee = mongoose.model("logs", schema_logs)
async function save_employee_save_logs(Log) {
  const save_employee = new save_log_employee()
  save_employee.id_employee = Log.id_employee
  save_employee.name_user = Log.name_employee
  save_employee.user_email = Log.email_employe
  save_employee.department_id = Log.department_id
  save_employee.date_enter_user = Log.date_enter
  await save_employee.save()
}

async function search_all_logs() {
  const all_logs = await save_log_employee.find();;
  console.log(all_logs)
  return all_logs
}


export { save_employee_save_logs, search_all_logs }



