import { Router } from "express";
import fetch from "node-fetch";
import { verifyToken, verifyAdmin } from "../middleware/auth.js";

const router = Router();

const LOGS_SERVICE_URL = process.env.LOGS_SERVICE_URL || "http://service-logs:8080";

const traceHeaders = (res) => ({
    ...(res.locals.traceparent && { traceparent: res.locals.traceparent }),
    ...(res.locals.tracestate && { tracestate: res.locals.tracestate }),
});

// GET /logs — obtener logs de onboarding
router.get("/", verifyToken, verifyAdmin, async (req, res) => {
    try {
        const response = await fetch(`${LOGS_SERVICE_URL}/notifications`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        const data = await response.json();
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "GET /logs" }));
        res.status(502).json({ error: "Error al conectar con el servicio de logs" });
    }
});

// GET /logs/delete — obtener logs de offboarding
router.get("/delete", verifyToken, verifyAdmin, async (req, res) => {
    try {
        const response = await fetch(`${LOGS_SERVICE_URL}/notifications/delete`, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                ...traceHeaders(res),
            },
        });

        const data = await response.json();
        res.status(response.status).json(data);
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "GET /logs/delete" }));
        res.status(502).json({ error: "Error al obtener los logs de desvinculación" });
    }
});

export { router as logRoutes };