import { Router } from "express";
import fetch from "node-fetch";
import { verifyToken } from "../middleware/auth.js";

const router = Router();

const PROFILE_SERVICE_URL = process.env.PROFILE_SERVICE_URL || "http://service-profile:8080";

const traceHeaders = (res) => ({
    ...(res.locals.traceparent && { traceparent: res.locals.traceparent }),
    ...(res.locals.tracestate && { tracestate: res.locals.tracestate }),
});

// GET /profile — obtener todos los perfiles
router.get("/", verifyToken, async (req, res) => {
    try {
        const response = await fetch(`${PROFILE_SERVICE_URL}/api/profiles`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        const data = await response.json();
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "GET /profile" }));
        res.status(502).json({ error: "Error al conectar con el servicio de perfiles" });
    }
});

// GET /profile/:id — obtener perfil por ID
router.get("/:id", verifyToken, async (req, res) => {
    try {
        const { id } = req.params;

        const response = await fetch(`${PROFILE_SERVICE_URL}/api/profiles/${id}`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        const data = await response.json();
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: `GET /profile/${req.params.id}` }));
        res.status(502).json({ error: "Error al obtener el perfil" });
    }
});

// POST /profile — crear perfil
router.post("/", verifyToken, async (req, res) => {
    try {
        const response = await fetch(`${PROFILE_SERVICE_URL}/api/profiles`, {
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
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "POST /profile" }));
        res.status(502).json({ error: "Error al crear el perfil" });
    }
});

// PUT /profile/:id — actualizar perfil
router.put("/:id", verifyToken, async (req, res) => {
    try {
        const { id } = req.params;

        const response = await fetch(`${PROFILE_SERVICE_URL}/api/profiles/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
            body: JSON.stringify(req.body),
        });

        const data = await response.json();
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: `PUT /profile/${req.params.id}` }));
        res.status(502).json({ error: "Error al actualizar el perfil" });
    }
});

export { router as profileRoutes };