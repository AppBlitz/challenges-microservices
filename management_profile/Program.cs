using Microsoft.EntityFrameworkCore;
using OpenTelemetry.Resources;
using OpenTelemetry.Trace;
using Prometheus;

var builder = WebApplication.CreateBuilder(args);

// ← AGREGAR AQUÍ
builder.Services.Configure<HostOptions>(options =>
{
    options.BackgroundServiceExceptionBehavior = BackgroundServiceExceptionBehavior.Ignore;
});


// OpenTelemetry
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

// Health checks
builder.Services.AddHealthChecks();

builder.Services.AddOpenApi();
builder.Services.AddControllers();

builder.Services.AddDbContext<DataContext>(options =>
    options.UseNpgsql(builder.Configuration.GetConnectionString("DefaultConnection")));

builder.Services.AddScoped<IProfileService, ProfileService>();
builder.Services.AddHostedService<RabbitMqConsumerService>();
builder.Services.AddScoped<IEmailService, EmailService>();
builder.Services.AddSwaggerGen();

var app = builder.Build();

using (var scope = app.Services.CreateScope())
{
    var db = scope.ServiceProvider.GetRequiredService<DataContext>();
    db.Database.Migrate();
}

if (app.Environment.IsDevelopment())
{
    app.MapOpenApi();
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

// Métricas Prometheus
app.UseHttpMetrics();
app.MapMetrics("/metrics");

// Health endpoint
app.MapHealthChecks("/health");

app.MapControllers();

app.Run();