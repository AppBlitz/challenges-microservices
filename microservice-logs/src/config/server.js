import { controller_notifications } from "../controller/controller_notifications.js";
let http;
try {
  http = await import("node:http");
} catch (error) {
  console.error(error)
}
const server = http.createServer({ keepAliveTimeout: 600000 }, (req, res) => {
  const { method, url } = req;
  console.log("this url", url.split("/")[1] == "notifications")
  if (method === "GET") {
    controller_notifications.get_notification_id_employee(req, res);
  } else {
    controller_notifications.not_allowed(req, res);
  }
})
export { server }
