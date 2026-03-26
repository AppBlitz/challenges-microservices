import { search_log } from "../services/service_logs.js";

/**
 * @swagger
 * components:
 *   schemas:
 *     Notification:
 *       type: object
 *       properties:
 *         id:
 *           type: string
 *           example: "log_8822"
 *         message:
 *           type: string
 *           example: "New login detected"
 *     ErrorResponse:
 *       type: object
 *       properties:
 *         error:
 *           type: string
 *           example: "Something went wrong"
 */

/**
 * @swagger
 * tags:
 *   - name: Notifications
 *     description: Endpoints to manage employee notification logs
 */

const notificationController = {

  /**
   * @swagger
   * /notifications:
   *   get:
   *     summary: Get employee notification logs
   *     description: Retrieves a list of all activity logs related to employees.
   *     tags:
   *       - Notifications
   *     responses:
   *       200:
   *         description: Successful list of logs
   *         content:
   *           application/json:
   *             schema:
   *               type: array
   *               items:
   *                 $ref: '#/components/schemas/Notification'
   *   all:
   *     summary: Handle not allowed methods
   *     description: This response occurs when an HTTP method other than GET is used.
   *     tags:
   *       - Notifications
   *     responses:
   *       405:
   *         description: Method not allowed
   *         content:
   *           application/json:
   *             schema:
   *               $ref: '#/components/schemas/ErrorResponse'
   */
  get_notification_id_employee: (req, res) => {
    res.writeHead(200, { 'Content-type': 'application/json' });
    res.end(search_log());
  },

  not_allowed: (_, res) => {
    res.writeHead(405, { 'Content-type': 'application/json' });
    res.end(JSON.stringify({ error: "Method not allowed" }));
  },

  /**
   * @swagger
   * /notifications/{id}:
   *   get:
   *     summary: Handle employee not found
   *     description: Returns an error when the requested employee ID does not exist.
   *     tags:
   *       - Notifications
   *     parameters:
   *       - in: path
   *         name: id
   *         required: true
   *         schema:
   *           type: string
   *         description: Unique identifier of the employee
   *     responses:
   *       404:
   *         description: Employee not found
   *         content:
   *           application/json:
   *             schema:
   *               $ref: '#/components/schemas/ErrorResponse'
   */
  not_found: (_, res) => {
    res.writeHead(404, { 'Content-type': 'application/json' });
    res.end(JSON.stringify({ error: "employee not found" }));
  },
};

export { notificationController };
