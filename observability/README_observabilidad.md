# Reto 7 — Observabilidad y Monitoreo

## 7. Pruebas del Sistema: Simulación del Caos

*(Ver documento adjunto con capturas de pantalla de los pasos 1 al 6)*

---

### ¿Qué servicio del ecosistema tardó más en responder y cómo lo identificaron?

El servicio que tardó más en responder fue **service-departments**, identificado mediante dos herramientas del stack de observabilidad:

**Grafana** — el panel de Latencia promedio mostró picos de hasta ~12.5 ms durante la simulación de caos, cuando se introdujo un retardo artificial de 3 segundos con probabilidad del 50% en el método `FinAllDepartments()` del servicio de departamentos.

**Zipkin** — las trazas distribuidas mostraron el tiempo de respuesta por servicio. La traza `auth-service: userrepository.findbyemail` con 4 spans y 444 ms fue la de mayor duración en condiciones normales, evidenciando que las operaciones de base de datos en el auth-service representan un punto de latencia relevante incluso sin caos inducido.

La combinación de métricas en Grafana y trazas en Zipkin permitió identificar tanto el servicio con mayor latencia inducida (service-departments) como el servicio con mayor latencia natural (auth-service en operaciones de base de datos).

---

## Diagrama de Arquitectura de Observabilidad

```
┌─────────────────────────────────────────────────────────────────┐
│                     ECOSISTEMA DE MICROSERVICIOS                │
│                                                                 │
│  ┌─────────────┐  ┌──────────────────┐  ┌──────────────────┐   │
│  │ auth-service│  │service-employees │  │service-departments│  │
│  │  :8086      │  │     :8081        │  │      :8083        │   │
│  │ Java/Spring │  │  Java/Spring     │  │       Go          │   │
│  └──────┬──────┘  └────────┬─────────┘  └────────┬──────────┘   │
│         │                  │                      │              │
│  ┌──────┴──────┐  ┌────────┴──────────────────────┴──────────┐  │
│  │service-logs │  │           service-profile                 │  │
│  │   :8085     │  │              :5095                        │  │
│  │  Node.js    │  │             .NET                          │  │
│  └─────────────┘  └───────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              │ métricas / trazas / logs
                              ▼
┌─────────────────────────────────────────────────────────────────┐
│                   STACK DE OBSERVABILIDAD                       │
│                                                                 │
│  ┌──────────────┐    ┌──────────────┐    ┌──────────────────┐  │
│  │  Prometheus  │    │    Grafana   │    │      Zipkin      │  │
│  │    :9090     │───▶│    :3000     │    │      :9411       │  │
│  │  (scraping)  │    │ (dashboards) │    │   (trazas dist.) │  │
│  └──────────────┘    └──────┬───────┘    └──────────────────┘  │
│                             │ alertas                           │
│  ┌──────────────┐           ▼                                   │
│  │     Loki     │    ┌──────────────┐                          │
│  │    :3100     │    │   Discord    │                          │
│  │    (logs)    │    │  (webhook)   │                          │
│  └──────┬───────┘    └──────────────┘                          │
│         ▲                                                       │
│  ┌──────┴───────┐                                               │
│  │   Promtail   │                                               │
│  │ (recolector) │                                               │
│  └──────────────┘                                               │
└─────────────────────────────────────────────────────────────────┘
```

---

## Investigación Conceptual

### Pull vs. Push

**Pull (Prometheus)** — el servidor de monitoreo consulta activamente los endpoints `/metrics` de cada servicio cada cierto intervalo de tiempo. El servicio no sabe que está siendo monitoreado; simplemente expone sus métricas y espera a ser consultado. Ventaja: control centralizado del ritmo de scraping y fácil detección de servicios caídos (si no responde, está DOWN).

**Push (Zipkin)** — cada servicio envía activamente sus trazas al servidor de trazabilidad en el momento en que ocurren. El servicio conoce la dirección del colector y envía los datos sin esperar a ser consultado. Ventaja: los datos llegan en tiempo real sin depender de un intervalo de scraping.

En este proyecto usamos **Pull para métricas** (Prometheus scraping `/actuator/prometheus` y `/metrics`) y **Push para trazas** (OTel Agent enviando spans a Zipkin en `http://zipkin:9411/api/v2/spans`).

### OpenTelemetry (OTel)

OpenTelemetry es un framework de observabilidad open-source que estandariza la recolección de trazas, métricas y logs. Proporciona SDKs para múltiples lenguajes y un agente Java (`opentelemetry-javaagent.jar`) que instrumenta automáticamente las aplicaciones sin modificar el código. En este proyecto se usa la versión 2.4.0 del agente Java para instrumentar `auth-service` y `service-employees`, mientras que Go, Node.js y .NET usan los SDKs específicos de OTel para cada plataforma.

### W3C Trace Context

W3C Trace Context es un estándar web que define cómo propagar el contexto de una traza distribuida entre servicios mediante cabeceras HTTP. Define dos cabeceras: `traceparent` (contiene el trace ID, span ID y flags de sampling) y `tracestate` (datos adicionales específicos del vendor). Gracias a este estándar, cuando `service-employees` llama a `service-departments`, el trace ID se propaga automáticamente en la cabecera HTTP, permitiendo que Zipkin agrupe todos los spans de una misma operación en una sola traza.

---

## Librerías Utilizadas por Microservicio

### Java — auth-service y service-employees (Spring Boot)

- `spring-boot-starter-actuator` — expone el endpoint `/actuator/prometheus` con métricas de la JVM y de la aplicación.
- `micrometer-registry-prometheus` — formatea las métricas de Micrometer en el formato que Prometheus puede leer.
- `opentelemetry-javaagent.jar v2.4.0` — agente que instrumenta automáticamente la aplicación para generar trazas distribuidas y enviarlas a Zipkin.

### Go — service-departments

- `github.com/prometheus/client_golang` — librería oficial de Prometheus para Go; expone el endpoint `/metrics` con métricas custom e histogramas de latencia HTTP.
- `go.opentelemetry.io/otel` — SDK de OpenTelemetry para Go para instrumentación de trazas.
- `go.opentelemetry.io/otel/exporters/zipkin` — exportador que envía los spans generados a Zipkin.

### Node.js — service-logs

- `prom-client` — librería para exponer métricas en formato Prometheus desde Node.js.
- `@opentelemetry/sdk-node ^0.57.0` — SDK de OTel para Node.js que configura el pipeline de trazabilidad.
- `@opentelemetry/exporter-zipkin ^1.30.0` — exportador de spans hacia Zipkin.

### .NET — service-profile (ASP.NET Core 10)

- `prometheus-net.AspNetCore` — middleware que expone el endpoint `/metrics` con métricas del runtime .NET y de las peticiones HTTP.
- `OpenTelemetry.Extensions.Hosting` — integración de OTel con el sistema de hosting de ASP.NET Core.
- `OpenTelemetry.Exporter.Zipkin` — exportador de trazas hacia Zipkin.

---

## Justificación: Zipkin vs. Jaeger

Se eligió **Zipkin** sobre Jaeger por las siguientes razones:

**Simplicidad de configuración** — Zipkin requiere una sola imagen Docker (`openzipkin/zipkin`) sin dependencias externas. Jaeger en su versión completa requiere múltiples componentes (collector, query, agent) o una imagen all-in-one que consume más recursos.

**Soporte nativo en el agente OTel Java** — el agente `opentelemetry-javaagent.jar` incluye soporte directo para el exporter de Zipkin mediante la propiedad `-Dotel.traces.exporter=zipkin`, sin necesidad de dependencias adicionales.

**Imagen Docker ligera** — la imagen `openzipkin/zipkin:latest` es significativamente más ligera que la de Jaeger, lo que reduce el tiempo de build y el consumo de recursos en el entorno de desarrollo local.

**Madurez y estabilidad** — Zipkin es uno de los sistemas de trazabilidad distribuida más antiguos y estables, con amplia documentación y comunidad activa.

---

## Canal de Alertas: Discord

Se eligió **Discord** como canal de notificaciones de alertas por su facilidad de configuración mediante webhooks y por ser una herramienta de comunicación ya utilizada en el equipo.

### Configuración del webhook en Grafana

1. En Discord, ir al canal deseado → **Editar canal** → **Integraciones** → **Webhooks** → **Nuevo Webhook** → copiar la URL.
2. En Grafana, ir a **Alerting** → **Contact points** → **Add contact point**.
3. Seleccionar tipo **Discord** y pegar la URL del webhook.
4. Guardar y usar ese contact point en las **Notification policies**.

Las alertas configuradas en este proyecto son:
- **Servicio Caído** — se dispara cuando `up == 0` durante 1 minuto para cualquier microservicio.
- **Alta Tasa de Errores** — se dispara cuando más del 10% de las peticiones retornan códigos 5xx en una ventana de 2 minutos.
