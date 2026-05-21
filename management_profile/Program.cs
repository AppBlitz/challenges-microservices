using Microsoft.EntityFrameworkCore;
using OpenTelemetry.Resources;
using OpenTelemetry.Trace;
using Prometheus;

/// <summary>
/// Punto de entrada principal del microservicio de perfiles.
/// Configura el contenedor de dependencias, la conexión a la base de datos,
/// el consumidor de RabbitMQ, el servicio de correo electrónico y la documentación OpenAPI.
/// </summary>
var builder = WebApplication.CreateBuilder(args);

/// <summary>
/// Configura el comportamiento de los servicios en segundo plano
/// para que no detengan la aplicación en caso de excepciones no controladas.
/// Mas específicamente, el servicio de consumo de RabbitMQ.
/// </summary>
builder.Services.Configure<HostOptions>(options =>
{
    options.BackgroundServiceExceptionBehavior = BackgroundServiceExceptionBehavior.Ignore;
});

/// <summary>
/// Configura OpenTelemetry para trazabilidad distribuida.
/// Exporta las trazas al servidor Zipkin definido en la configuración.
/// </summary>
builder.Services.AddOpenTelemetry()
    .WithTracing(tracing => tracing
        .SetResourceBuilder(ResourceBuilder.CreateDefault()
            .AddService(builder.Configuration["OTEL_SERVICE_NAME"] ?? "service-profile"))
        .AddAspNetCoreInstrumentation()
        .AddZipkinExporter(options =>
        {
            options.Endpoint = new Uri(
                builder.Configuration["OTEL_EXPORTER_ZIPKIN_ENDPOINT"]
                ?? "http://zipkin:9411/api/v2/spans");
        }));

/// <summary>
/// Agrega health checks para monitorear el estado del servicio.
/// </summary>
builder.Services.AddHealthChecks();

/// <summary>
/// Agrega soporte para documentación automática de la API mediante OpenAPI.
/// Permite visualizar los endpoints disponibles en entorno de desarrollo.
/// </summary>
builder.Services.AddOpenApi();

/// <summary>
/// Agrega soporte para controladores REST.
/// Habilita el uso de endpoints definidos mediante atributos [ApiController].
/// </summary>
builder.Services.AddControllers();

/// <summary>
/// Configura el contexto de base de datos utilizando Entity Framework Core
/// con proveedor PostgreSQL.
/// La cadena de conexión se obtiene desde appsettings.json.
/// </summary>
builder.Services.AddDbContext<DataContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

/// <summary>
/// Registra el servicio de lógica de negocio de perfiles en el contenedor
/// de dependencias para permitir su inyección en controladores y servicios.
/// </summary>
builder.Services.AddScoped<IProfileService, ProfileService>();

/// <summary>
/// Registra el servicio en segundo plano encargado de consumir mensajes
/// desde RabbitMQ.
/// Permite sincronizar automáticamente la creación y eliminación de perfiles
/// desde otros microservicios.
/// </summary>
builder.Services.AddHostedService<RabbitMqConsumerService>();

/// <summary>
/// Registra el servicio de envío de correos electrónicos.
/// Se utiliza para notificar al usuario cuando su perfil ha sido creado.
/// </summary>
builder.Services.AddScoped<IEmailService, EmailService>();

/// <summary>
/// Registra el servicio de generación de documentación OpenAPI mediante Swagger.
/// </summary>
builder.Services.AddSwaggerGen();

var app = builder.Build();

/// <summary>
/// Aplica automáticamente las migraciones pendientes de Entity Framework Core
/// al iniciar la aplicación.
/// Garantiza que la base de datos esté sincronizada con el modelo actual.
/// </summary>
using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<DataContext>();
    db.Database.Migrate();
}

/// <summary>
/// Configura la documentación interactiva de la API únicamente en entorno
/// de desarrollo.
/// Habilita la generación de la especificación OpenAPI y la interfaz Swagger UI,
/// permitiendo inspeccionar y probar los endpoints directamente desde el navegador.
/// </summary>
if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
    app.UseSwagger();
    app.UseSwaggerUI();
}

/// <summary>
/// Habilita redirección automática hacia HTTPS.
/// Mejora la seguridad en las comunicaciones cliente-servidor.
/// </summary>
app.UseHttpsRedirection();

/// <summary>
/// Habilita la recolección de métricas HTTP para Prometheus.
/// </summary>
app.UseHttpMetrics();

/// <summary>
/// Expone el endpoint de métricas Prometheus en /metrics.
/// </summary>
app.MapMetrics("/metrics");

/// <summary>
/// Expone el endpoint de health check en /health.
/// </summary>
app.MapHealthChecks("/health");

/// <summary>
/// Mapea los endpoints definidos en los controladores REST.
/// Activa el enrutamiento de la API.
/// </summary>
app.MapControllers();

/// <summary>
/// Inicia la ejecución del microservicio.
/// </summary>
app.Run();