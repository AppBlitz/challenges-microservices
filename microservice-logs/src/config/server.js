import { notificationController } from "../controller/controller_notifications.js";
import documentation_controller from "../controller/controller_documentation.js";
import http from "node:http"; // Simpler way to import in ES Modules

const server = http.createServer({ keepAliveTimeout: 600000 }, (req, res) => {
  try {
    const { method, url } = req;
    // We get the first part of the path, for example: "notifications" or "docs"
    const path = url.split("/")[1];

    if (method === "GET" && path === "notifications") {
      notificationController.get_notification_id_employee(req, res);

    } else if (method === "GET" && (path === "docs" || path === "swagger.json")) {
      documentation_controller.documentation_swagger(req, res);

    } else {
      // If no route matches, return a 404 instead of letting the request hang
      res.writeHead(404, { 'Content-Type': 'application/json' });
      res.end(JSON.stringify({ error: "Route not found" }));
    }
  } catch (error) {
    // This block catches fatal errors and prevents Status: 0
    console.error("Server Error:", error);
    res.writeHead(500, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ error: "Internal Server Error" }));
  }
});

export { server };
