import { search_log, search_delete_log } from "../services/service_logs.js";



const notificationController = {


    get_notification_id_employee: async (_, res) => {
        res.writeHead(200, { 'Content-type': 'application/json' });
        let response = await search_log();
        res.end(JSON.stringify(response));
    },

    get_delete_notification: async (_, res) => {
        res.writeHead(200, { 'Content-type': 'application/json' });
        let response = await search_delete_log();
        res.end(JSON.stringify(response));
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
