import mongoose from "mongoose"
const user_mongo = process.env.MONGO_INITDB_ROOT_USERNAME
const user_password = process.env.MONGO_INITDB_ROOT_PASSWORD
const name_database = process.env.MONGO_INITDB_DATABASE
const host_database = process.env.MONGO_HOST
const port_database = process.env.MONGO_PORT
const uri = `mongodb://${user_mongo}:${user_password}@${host_database}:${port_database}/${name_database}?authSource=admin`
const { Schema, connect } = mongoose
await connect(uri)
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
  try {
    console.log("data to save", Log);

    const save_employee = new save_log_employee({
      id_employee: Log.ID_employee,
      name_user: Log.name_employee,
      user_email: Log.email_employe,
      department_id: Log.department_id,
      date_enter_user: Log.date_enter
    });

    await save_employee.save();

  } catch (error) {
    console.error("❌ Error al guardar:", error.message);
  }
}

async function search_all_logs() {
  const all_logs = await save_log_employee.find();;
  return all_logs
}


export { save_employee_save_logs, search_all_logs }