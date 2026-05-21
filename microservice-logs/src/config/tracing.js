import { NodeSDK } from "@opentelemetry/sdk-node";
import { ZipkinExporter } from "@opentelemetry/exporter-zipkin";
import { getNodeAutoInstrumentations } from "@opentelemetry/auto-instrumentations-node";
import { Resource } from "@opentelemetry/resources";
import { SEMRESATTRS_SERVICE_NAME } from "@opentelemetry/semantic-conventions";

const sdk = new NodeSDK({
    resource: new Resource({
        [SEMRESATTRS_SERVICE_NAME]: process.env.OTEL_SERVICE_NAME || "service-logs",
    }),
    traceExporter: new ZipkinExporter({
        url: process.env.OTEL_EXPORTER_ZIPKIN_ENDPOINT || "http://zipkin:9411/api/v2/spans",
    }),
    instrumentations: [getNodeAutoInstrumentations()],
});

sdk.start();

process.on("SIGTERM", () => {
    sdk.shutdown().finally(() => process.exit(0));
});