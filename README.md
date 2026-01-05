# Sentiment Analysis Backend (Demo)

Backend en Spring Boot para análisis de sentimientos. Actúa como orquestador/proxy entre el cliente y el modelo de IA (FastAPI).

## 🚀 Quickstart (30 segundos)

### Prerrequisitos
- Java 17+
- Maven (o usar `./mvnw`)
- Docker (opcional)

### Ejecutar en modo "Mock" (Por defecto)
El backend simulará respuestas sin necesitar el modelo de Python levantado.

```bash
./mvnw spring-boot:run
```

El servicio estará disponible en: `http://localhost:8080`

### Endpoints Principales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST   | `/sentiment` | Analiza el texto enviado. Body: `{ "text": "..." }` |
| GET    | `/health` | Estado del servicio. |
| GET    | `/health/model` | Verifica conexión con el modelo de IA. |
| GET    | `/stats` | Estadísticas de uso en memoria. |

## ⚙️ Configuración

El comportamiento se controla vía `application.properties` o variables de entorno:

- `SENTIMENT_MODE`: `mock` (default) o `python`.
- `PYTHON_API_URL`: URL del servicio FastAPI (ej. `http://localhost:8000/predict`).

## 🛠️ Arquitectura y Mejoras

- **Resiliencia**: Timeouts configurados y manejo de errores 503 si el modelo cae.
- **Trazabilidad**: `X-Request-Id` único por petición para depuración.
- **Calidad**: DTOs estrictos y validaciones de entrada.

## 🧪 Testing

El proyecto cuenta con una suite de pruebas automáticas y scripts de verificación rápida. Consulta TESTING.md para más detalles.

## 🐳 Docker

```bash
docker-compose up --build
```