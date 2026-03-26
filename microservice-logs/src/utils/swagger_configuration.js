import swaggerJsdoc from "swagger-jsdoc";
import path from "node:path";

const options = {
  definition: {
    openapi: "3.0.0",
    info: {
      title: "Employee Notifications API",
      version: "1.0.0",
    },
  },
  // Use path.resolve to create a fixed address to your controllers
  apis: [
    path.resolve("src/controller/*.js"),
    path.resolve("src/controllers/*.js") // Added 's' just in case
  ],
};

const openapiSpecification = swaggerJsdoc(options);


export default openapiSpecification;
