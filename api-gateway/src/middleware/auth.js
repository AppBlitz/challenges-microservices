import jwt from "jsonwebtoken";

const JWT_SECRET = process.env.JWT_SECRET || "miClaveSuperSecretaParaJwtQueDebeSerLarga2024!!";

export const verifyToken = (req, res, next) => {
    const authHeader = req.headers["authorization"];

    if (!authHeader || !authHeader.startsWith("Bearer ")) {
        return res.status(401).json({ error: "Token no proporcionado" });
    }

    const token = authHeader.substring(7);

    try {
        const decoded = jwt.verify(token, JWT_SECRET);
        req.user = decoded;
        next();
    } catch (error) {
        return res.status(401).json({ error: "Token inválido o expirado" });
    }
};

export const verifyAdmin = (req, res, next) => {
    if (!req.user) {
        return res.status(401).json({ error: "No autenticado" });
    }
    if (req.user.role !== "ADMIN") {
        return res.status(403).json({ error: "Se requiere rol ADMIN" });
    }
    next();
};