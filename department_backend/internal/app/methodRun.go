package app

import (
	"context"
	"database/sql"
	"log"
	"net/http"
	"os"

	"github.com/AppBlitz/department_backend/internal/repository"
	"github.com/AppBlitz/department_backend/internal/service"
	"github.com/AppBlitz/department_backend/internal/transport/https"
	"go.opentelemetry.io/otel"
	"go.opentelemetry.io/otel/exporters/zipkin"
	"go.opentelemetry.io/otel/sdk/resource"
	sdktrace "go.opentelemetry.io/otel/sdk/trace"
	semconv "go.opentelemetry.io/otel/semconv/v1.21.0"
)

func initTracer() func() {
	zipkinURL := os.Getenv("OTEL_EXPORTER_ZIPKIN_ENDPOINT")
	if zipkinURL == "" {
		zipkinURL = "http://zipkin:9411/api/v2/spans"
	}

	exporter, err := zipkin.New(zipkinURL)
	if err != nil {
		log.Printf("Error creating Zipkin exporter: %v", err)
		return func() {}
	}

	serviceName := os.Getenv("OTEL_SERVICE_NAME")
	if serviceName == "" {
		serviceName = "service-departments"
	}

	tp := sdktrace.NewTracerProvider(
		sdktrace.WithBatcher(exporter),
		sdktrace.WithResource(resource.NewWithAttributes(
			semconv.SchemaURL,
			semconv.ServiceName(serviceName),
		)),
	)

	otel.SetTracerProvider(tp)

	return func() {
		if err := tp.Shutdown(context.Background()); err != nil {
			log.Printf("Error shutting down tracer: %v", err)
		}
	}
}

func Run(db *sql.DB) {
	shutdown := initTracer()
	defer shutdown()

	repo := repository.NewDepartmentRepository(db)
	servi := service.NewDepartmentService(repo)
	trans := https.NewDepartmentHandler(servi)

	portListen := os.Getenv("PORT_LISTEN")

	erro := http.ListenAndServe(":"+portListen, https.AllHandlers(trans))
	if erro != nil {
		log.Fatal("Erro in server: ", erro)
	}
}