# 📋 Reporte de Estado - Rama QA/Docs (Dev 4)

**Fecha:** Semana 2 - Cierre de Sprint
**Autor:** Dev 4 (QA/Docs)
**Estado General:** 🟢 LISTO PARA PR (Backend + Docs + Tests)

## ✅ Entregables Completados

1.  **Documentación (`README.md`)**:
    *   Instrucciones "Quickstart" para el jurado.
    *   Tabla de endpoints actualizada.
    *   Explicación de modos `mock` vs `python`.

2.  **Calidad y Pruebas**:
    *   **Tests Unitarios**: `SentimentControllerTest.java` cubre casos de éxito (200), validación (400) y stats.
    *   **Verificación**: ✅ Ejecutados y pasando (`BUILD SUCCESS`).
    *   **Postman**: Colección actualizada con `/stats` y `/health/model`.

3.  **Infraestructura**:
    *   **Docker**: `Dockerfile` multi-stage (Java 17) y `docker-compose.yml` listos.
    *   **Resiliencia**: Configurados timeouts en `AppConfig` y manejo de errores 503/502 en `GlobalExceptionHandler`.

## ⚠️ Pendientes / Bloqueantes (Otros Roles)

Para que la demo sea exitosa, necesitamos integrar lo siguiente:

*   **Dev 3 (Frontend)**:
    *   Falta el archivo `index.html` o la UI web. Actualmente solo tenemos API.
    *   *Sugerencia*: Agregar un HTML simple en `src/main/resources/static` para servirlo automáticamente.

*   **Dev 1 (Integración Python)**:
    *   Validar que el contenedor de Python responda con el JSON exacto: `{"prevision": "POSITIVO", "probabilidad": 0.9}`.
    *   El backend ya espera este formato. Si Python devuelve `sentiment` o minúsculas, fallará.

## 🚀 Cómo probar esta rama

1.  **Tests Automáticos**:
    `./mvnw test` (Debe pasar todo en verde).

2.  **Ejecución Local (Mock)**:
    `./mvnw spring-boot:run`

3.  **Verificación Rápida**:
    En otra terminal: `./manual_tests.sh`
    *(Valida Health, Sentiment, Stats y History en 1 segundo)*.