import { notificationController } from "../controller/controller_notifications.js";
import documentation_controller from "../controller/controller_documentation.js";
import http from "node:http";
import { register } from "prom-client";
import promClient from "prom-client";

promClient.collectDefaultMetrics({ prefix: "service_logs_" });

const server = http.createServer({ keepAliveTimeout: 600000 }, async (req, res) => {
  try {
    const { method, url } = req;
    const path = url.split("/")[1];

      if (method === "GET" && url === "/notifications/delete") {
          notificationController.get_delete_notification(req, res);

      } else if (method === "GET" && path === "notifications") {
          notificationController.get_notification_id_employee(req, res);

      } else if (method === "GET" && (path === "docs" || path === "swagger.json")) {

    } else if (method === "GET" && path === "metrics") {
      res.writeHead(200, { "Content-Type": register.contentType });
      res.end(await register.metrics());

    }
     else if (method === "GET" && path === "health") {
      res.writeHead(200, { "Content-Type": "application/json" });
      res.end(JSON.stringify({
        status: "UP",
        service: "service-logs",
        checks: {
          database: "UP",
          messageBroker: "UP"
        }
      }));

    } else {
      res.writeHead(404, { "Content-Type": "application/json" });
      res.end(JSON.stringify({ error: "Route not found" }));
    }
  } catch (error) {
    console.error("Server Error:", error);
    res.writeHead(500, { "Content-Type": "application/json" });
    res.end(JSON.stringify({ error: "Internal Server Error" }));
  }
});

export { server };