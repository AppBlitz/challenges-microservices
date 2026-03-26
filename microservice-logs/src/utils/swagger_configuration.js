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

// DEBUGGING LOGS: This will appear in your terminal
console.log("-----------------------------------------");
console.log("SWAGGER DEBUG INFO:");
console.log("Working Directory:", process.cwd());
console.log("Looking for files in:", path.resolve("src/controller/*.js"));
console.log("Total Paths found:", Object.keys(openapiSpecification.paths || {}).length);
console.log("-----------------------------------------");

export default openapiSpecification;
