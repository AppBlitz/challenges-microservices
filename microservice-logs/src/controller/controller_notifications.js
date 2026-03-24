import { search_log } from "../services/service_logs.js"
const controller_notifications = {

  get_notification_id_employee: (req, res) => {
    res.writeHead(200, { 'Content-type': 'Application/json' })
    res.end(search_log());
  },
  not_allowed: (_, res) => {
    res.writeHead(405, { 'Content-type': 'Application/json' })
    res.end("Method not allowed,");
  },
  not_found: (_, res) => {
    res.writeHead(404, { 'Content-type': 'Application/json' })
    res.end("employee not found")
  },

}

export { controller_notifications }
