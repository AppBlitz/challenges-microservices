import openapiSpecification from "../utils/swagger_configuration.js";

const documentation_controller = {
  /**
   * This method acts as a router for documentation-related requests.
   * It serves either the JSON specification or the visual UI.
   */
  documentation_swagger: function (req, res) {
    // 1. Serve the raw JSON Specification
    if (req.url === '/swagger.json') {
      res.writeHead(200, { 'Content-Type': 'application/json' });
      return res.end(JSON.stringify(openapiSpecification));
    }

    // 2. Serve the Visual Swagger UI (HTML)
    if (req.url === '/docs') {
      res.writeHead(200, { 'Content-Type': 'text/html' });
      return res.end(`
        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="utf-8" />
          <meta name="viewport" content="width=device-width, initial-scale=1" />
          <title>API Documentation - Swagger UI</title>
          <link rel="stylesheet" href="https://unpkg.com/swagger-ui-dist@5/swagger-ui.css" />
        </head>
        <body>
          <div id="swagger-ui"></div>
          <script src="https://unpkg.com/swagger-ui-dist@5/swagger-ui-bundle.js"></script>
          <script>
            window.onload = () => {
              window.ui = SwaggerUIBundle({
                url: '/swagger.json', // Points to the JSON logic above
                dom_id: '#swagger-ui',
                deepLinking: true,
                presets: [
                  SwaggerUIBundle.presets.apis,
                ],
              });
            };
          </script>
        </body>
        </html>
      `);
    }

    // Default error if the specific path isn't found within this controller
    res.writeHead(404, { 'Content-Type': 'application/json' });
    res.end(JSON.stringify({ error: "Documentation route not found" }));
  }
};

export default documentation_controller;
