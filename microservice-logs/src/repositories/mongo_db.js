import mongoose from "mongoose"
const user_mongo = process.env.MONGO_INITDB_ROOT_USERNAME
const user_password = process.env.MONGO_INITDB_ROOT_PASSWORD
const name_database = process.env.MONGO_INITDB_DATABASE
const host_database = process.env.MONGO_HOST
const port_database = process.env.MONGO_PORT
const uri = `mongodb://${user_mongo}:${user_password}@${host_database}:${port_database}/${name_database}?authSource=admin`
const { Schema, connect } = mongoose
await connect(uri)

const schema_logs = new Schema({
    id_employee: Number,
    name_user: String,
    user_email: String,
    department_id: Number,
    date_enter_user: Date
})

// Schema para logs de desvinculación
const schema_logs_delete = new Schema({
    id_employee: Number,
    name_user: String,
    user_email: String,
    type: String,       // "DESVINCULACION" para identificar el tipo
    date_delete: Date   // fecha de desvinculación
})

const save_log_employee = mongoose.model("logs", schema_logs)
const save_log_delete_employee = mongoose.model("logs_delete", schema_logs_delete, "logs_deletes")

async function save_employee_save_logs(Log) {
    try {
        const data = typeof Log === 'string' ? JSON.parse(Log) : Log;
        console.log("data to save", data);

        const save_employee = new save_log_employee({
            id_employee: data.ID_employee,
            name_user: data.name_employee,
            user_email: data.email_employee,
            department_id: data.department_id,
            date_enter_user: data.date_enter
        });

        await save_employee.save();

    } catch (error) {
        console.error("❌ Error al guardar:", error.message);
    }
}

// NUEVA FUNCIÓN — guarda log de desvinculación
async function save_employee_delete_logs(data) {
    try {
        console.log("data to save delete log", data);

        const log_delete = new save_log_delete_employee({
            id_employee: data.id_employee,
            name_user: data.name_employee,
            user_email: data.email_employee,
            type: "DESVINCULACION",
            date_delete: new Date()
        });

        await log_delete.save();

    } catch (error) {
        console.error("❌ Error al guardar log de desvinculación:", error.message);
    }
}

async function search_all_logs() {
    const all_logs = await save_log_employee.find();
    return all_logs
}

// NUEVA FUNCIÓN — busca todos los logs de desvinculación
async function search_all_delete_logs() {
    const all_logs = await save_log_delete_employee.find();
    return all_logs
}

export { save_employee_save_logs, search_all_logs, save_employee_delete_logs, search_all_delete_logs }



