import { server } from "./src/config/server.js";

const PORT = process.env.PORT || 8080;

server.listen(PORT, "0.0.0.0", () => {
    console.log(`API Gateway corriendo en el puerto ${PORT}`);
});