import { Router } from "express";
import fetch from "node-fetch";

const router = Router();

const AUTH_SERVICE_URL = process.env.AUTH_SERVICE_URL || "http://auth-service:8080";

const traceHeaders = (res) => ({
    ...(res.locals.traceparent && { traceparent: res.locals.traceparent }),
    ...(res.locals.tracestate && { tracestate: res.locals.tracestate }),
});

const fetchAndRespond = async (url, method, body, res, route) => {
    const response = await fetch(url, {
        method,
        headers: {
            "Content-Type": "application/json",
            ...traceHeaders(res),
        },
        body: body ? JSON.stringify(body) : undefined,
    });

    const text = await response.text();
    let data;
    try {
        data = text ? JSON.parse(text) : { message: "OK" };
    } catch {
        data = { message: text || "OK" };
    }
    res.status(response.status).json(data);
};

// POST /auth/login
router.post("/login", async (req, res) => {
    try {
        await fetchAndRespond(`${AUTH_SERVICE_URL}/auth/login`, "POST", req.body, res, "/auth/login");
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "/auth/login" }));
        res.status(502).json({ error: "Error al conectar con el servicio de autenticación" });
    }
});

// POST /auth/recover-password
router.post("/recover-password", async (req, res) => {
    try {
        await fetchAndRespond(`${AUTH_SERVICE_URL}/auth/recover-password`, "POST", req.body, res, "/auth/recover-password");
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "/auth/recover-password" }));
        res.status(502).json({ error: "Error al conectar con el servicio de autenticación" });
    }
});

// POST /auth/reset-password
router.post("/reset-password", async (req, res) => {
    try {
        await fetchAndRespond(`${AUTH_SERVICE_URL}/auth/reset-password`, "POST", req.body, res, "/auth/reset-password");
    } catch (error) {
        console.error(JSON.stringify({ service: "api-gateway", error: error.message, route: "/auth/reset-password" }));
        res.status(502).json({ error: "Error al conectar con el servicio de autenticación" });
    }
});

export { router as authRoutes };