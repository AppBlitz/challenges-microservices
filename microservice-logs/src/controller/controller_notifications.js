import { search_log } from "../services/service_logs.js";


const notificationController = {

  get_notification_id_employee: (req, res) => {
    res.writeHead(200, { 'Content-type': 'application/json' });
    res.end(search_log());
  },

  not_allowed: (_, res) => {
    res.writeHead(405, { 'Content-type': 'application/json' });
    res.end(JSON.stringify({ error: "Method not allowed" }));
  },

  not_found: (_, res) => {
    res.writeHead(404, { 'Content-type': 'application/json' });
    res.end(JSON.stringify({ error: "employee not found" }));
  },
};

export { notificationController };
