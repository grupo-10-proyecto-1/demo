# Plan Dev 4 — QA / Docs / Demo

Resumen de tareas y decisiones (guardado para arrancar mañana):

## Objetivo
Preparar los artefactos que sumen puntos en el hackathon: tests básicos, colección Postman, README "Modo Jurado", health endpoints y documentación para demo.

## Tareas (visión breve)
- [x] Tests MockMvc: 400 cuando falta `text`, 200 cuando `text` válido.
- Colección Postman: ejemplos "bonitos" y variable `{{baseUrl}}` → `docs/postman/sentiment.postman_collection.json`.
- README modo jurado: "Quickstart (30s)", Mock mode vs Python mode, ejemplos, troubleshooting.
- Docker (opcional): `Dockerfile` + `docker-compose.yml` placeholder para FastAPI.
- GET /stats (opcional): contador en memoria y endpoint.
- [x] GET /health y /health/model: health básica implementada (`/health`).

## Estrategia de trabajo
- Empezamos mañana: NO tocaremos `main` directamente.
- Rama sugerida: `feature/dev4-qa` (o `feat/dev4/qa`) — decidir prefieres alguno diferente.
- Flujo recomendado:
  1. Crear la rama desde `main`: `git checkout -b feature/dev4-qa`.
  2. Implementar cambios y tests localmente.
  3. Hacer commits pequeños y claros.
  4. Abrir PR cuando quieras revisión.

## Notas y restricciones
- Evitaremos tocar `pom.xml` si el hackathon exige no añadir dependencias; la mayoría de las tareas son realizables sin cambiar `pom`.
- [x] Para validación automática con `@NotBlank` necesitaríamos `spring-boot-starter-validation` (Agregado en Sesión 2).

## Estado Actual
- Rama `dev-4-qa` activa.
- Migración a JDK 17 completada.
- Tests MockMvc y Health pasando (`BUILD SUCCESS`) en Windows y Linux.
- Integración con cambios de Dev 2 y Dev 3 completada.

---

Plan actualizado tras sesión de estabilización.