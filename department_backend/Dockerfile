# Usa la imagen oficial de Golang como base
FROM golang:1.26-alpine AS builder

# Establece variables de entorno
ENV CGO_ENABLED=0 \
    GOOS=linux \
    GOARCH=amd64

# Establece el directorio de trabajo
WORKDIR /build

# Copia los archivos de dependencias
COPY go.mod go.sum ./

# Descarga las dependencias
RUN go mod download

# Copia TODO el código fuente
COPY . .

# Verifica que los archivos Go existan
# RUN ls -la /build

# Compila el binario
RUN go build -o /app/myapp ./cmd/app/main.go

# Etapa final (ligera)
FROM alpine:3.21 AS final

# Copia el binario compilado
COPY --from=builder /app/myapp /bin/app

# Expone el puerto
EXPOSE 8080

# Ejecuta la aplicación
CMD ["/bin/app"]
