import express from "express";
import { register, collectDefaultMetrics, Counter, Histogram } from "prom-client";
import { authRoutes } from "../routes/auth.routes.js";
import { employeeRoutes } from "../routes/employees.routes.js";
import { profileRoutes } from "../routes/profile.routes.js";
import { departmentRoutes } from "../routes/departments.routes.js";
import { logRoutes } from "../routes/logs.routes.js";

collectDefaultMetrics({ prefix: "api_gateway_" });

const httpRequestsTotal = new Counter({
    name: "api_gateway_http_requests_total",
    help: "Total de peticiones HTTP recibidas por el gateway",
    labelNames: ["method", "route", "status_code"],
});

const httpRequestDuration = new Histogram({
    name: "api_gateway_http_request_duration_seconds",
    help: "Duración de las peticiones HTTP en segundos",
    labelNames: ["method", "route", "status_code"],
    buckets: [0.1, 0.3, 0.5, 1, 2, 5],
});

const server = express();

server.use(express.json());

// Middleware de métricas y logs estructurados
server.use((req, res, next) => {
    const start = Date.now();

    res.on("finish", () => {
        const duration = (Date.now() - start) / 1000;
        const route = req.path;
        const method = req.method;
        const status = res.statusCode.toString();

        httpRequestsTotal.labels(method, route, status).inc();
        httpRequestDuration.labels(method, route, status).observe(duration);

        // Log estructurado JSON para Loki/Promtail
        console.log(JSON.stringify({
            timestamp: new Date().toISOString(),
            service: "api-gateway",
            method,
            route,
            status_code: res.statusCode,
            duration_ms: Date.now() - start,
        }));
    });

    // Propagación W3C Trace Context para Zipkin
    if (req.headers["traceparent"]) {
        res.locals.traceparent = req.headers["traceparent"];
    }
    if (req.headers["tracestate"]) {
        res.locals.tracestate = req.headers["tracestate"];
    }

    next();
});

// Health check
server.get("/health", (req, res) => {
    res.json({ status: "UP", service: "api-gateway" });
});

// Métricas Prometheus
server.get("/metrics", async (req, res) => {
    res.setHeader("Content-Type", register.contentType);
    res.end(await register.metrics());
});

// Rutas
server.use("/auth", authRoutes);
server.use("/employees", employeeRoutes);
server.use("/departments", departmentRoutes);
server.use("/profile", profileRoutes);
server.use("/logs", logRoutes);

// 404
server.use((req, res) => {
    res.status(404).json({ error: "Ruta no encontrada" });
});

export { server };