import { Router } from "express";
import fetch from "node-fetch";
import { verifyToken, verifyAdmin } from "../middleware/auth.js";

const router = Router();

const EMPLOYEES_SERVICE_URL = process.env.EMPLOYEES_SERVICE_URL || "http://service-employees:8080";
const PROFILE_SERVICE_URL = process.env.PROFILE_SERVICE_URL || "http://service-profile:8080";

const traceHeaders = (res) => ({
    ...(res.locals.traceparent && { traceparent: res.locals.traceparent }),
    ...(res.locals.tracestate && { tracestate: res.locals.tracestate }),
});

// GET /employees — listar todos
router.get("/", verifyToken, async (req, res) => {
    try {
        const response = await fetch(`${EMPLOYEES_SERVICE_URL}/employee/all/`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        if (response.status === 204) {
            return res.status(200).json([]);
        }

        const data = await response.json();
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "GET /employees" }));
        res.status(502).json({ error: "Error al conectar con el servicio de empleados" });
    }
});

// GET /employees/:id — obtener empleado completo (datos + perfil)
router.get("/:id", verifyToken, async (req, res) => {
    try {
        const { id } = req.params;

        const [employeeResponse, profilesResponse] = await Promise.all([
            fetch(`${EMPLOYEES_SERVICE_URL}/employee/retrieve/${id}`, {
                method: "GET",
                headers: { "Content-Type": "application/json", ...traceHeaders(res) },
            }),
            fetch(`${PROFILE_SERVICE_URL}/api/profiles`, {
                method: "GET",
                headers: { "Content-Type": "application/json", ...traceHeaders(res) },
            }),
        ]);

        if (!employeeResponse.ok) {
            const error = await employeeResponse.json();
            return res.status(employeeResponse.status).json(error);
        }

        const employee = await employeeResponse.json();
        const profiles = await profilesResponse.json();

        const profile = profiles.find(
            (p) => p.email === employee.emailEmployee || p.email === employee.email
        ) || null;

        res.status(200).json({ ...employee, profile });
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: `GET /employees/${req.params.id}` }));
        res.status(502).json({ error: "Error al obtener el empleado" });
    }
});

// POST /employees — crear empleado
router.post("/", verifyToken, verifyAdmin, async (req, res) => {
    try {
        const response = await fetch(`${EMPLOYEES_SERVICE_URL}/employee/save/`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
            body: JSON.stringify(req.body),
        });

        const data = await response.json();
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "POST /employees" }));
        res.status(502).json({ error: "Error al crear el empleado" });
    }
});

// DELETE /employees/:id — eliminar empleado
router.delete("/:id", verifyToken, verifyAdmin, async (req, res) => {
    try {
        const { id } = req.params;

        const response = await fetch(`${EMPLOYEES_SERVICE_URL}/employee/delete/${id}`, {
            method: "DELETE",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        res.status(response.status).json({ message: "Empleado eliminado correctamente" });
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: `DELETE /employees/${req.params.id}` }));
        res.status(502).json({ error: "Error al eliminar el empleado" });
    }
});

export { router as employeeRoutes };