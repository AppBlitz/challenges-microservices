import { Router } from "express";
import fetch from "node-fetch";
import { verifyToken, verifyAdmin } from "../middleware/auth.js";

const router = Router();

const DEPARTMENTS_SERVICE_URL = process.env.DEPARTMENTS_SERVICE_URL || "http://service-departments:8080";

const traceHeaders = (res) => ({
    ...(res.locals.traceparent && { traceparent: res.locals.traceparent }),
    ...(res.locals.tracestate && { tracestate: res.locals.tracestate }),
});

// GET /departments — listar todos
router.get("/", verifyToken, async (req, res) => {
    try {
        const response = await fetch(`${DEPARTMENTS_SERVICE_URL}/department/all/`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        const text = await response.text();
        const data = text ? JSON.parse(text) : [];
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "GET /departments" }));
        res.status(502).json({ error: "Error al conectar con el servicio de departamentos" });
    }
});

// GET /departments/:id — obtener por ID
router.get("/:id", verifyToken, async (req, res) => {
    try {
        const { id } = req.params;

        const response = await fetch(`${DEPARTMENTS_SERVICE_URL}/department/search/${id}/`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        const text = await response.text();
        const data = text ? JSON.parse(text) : {};
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: `GET /departments/${req.params.id}` }));
        res.status(502).json({ error: "Error al obtener el departamento" });
    }
});

// POST /departments — crear
router.post("/", verifyToken, verifyAdmin, async (req, res) => {
    try {
        const response = await fetch(`${DEPARTMENTS_SERVICE_URL}/department/save/`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
            body: JSON.stringify(req.body),
        });

        const text = await response.text();
        let data;
        try {
            data = text ? JSON.parse(text) : { message: "Departamento creado correctamente" };
        } catch {
            data = { message: text || "Departamento creado correctamente" };
        }
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "POST /departments" }));
        res.status(502).json({ error: "Error al crear el departamento" });
    }
});

export { router as departmentRoutes };