import { connectionRabbitMq } from "./src/config/RabbitMq.js";
import { server } from "./src/config/server.js";
connectionRabbitMq();
server.listen(8080, "0.0.0.0");
