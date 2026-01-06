# Bitácora de Avance - Equipo Dev 4 (QA & Docs)

Este documento sirve como registro de las actividades realizadas, decisiones tomadas y soluciones implementadas por Omar toledo Dev4 - QA y Documentación.


## 📅 Sesión 1: Preparación para Demo y Calidad Base

### 🎯 Objetivos
Cumplir con los entregables definidos en `TareasSeman1.txt` para el rol Dev 4: Tests, Documentación, Postman y Docker.

### ✅ Tareas Realizadas

1.  **Refactorización del README (Modo Jurado)**
    *   **Acción:** Se limpió el archivo `README.md` eliminando instrucciones redundantes y enfocándolo en la experiencia del usuario/jurado.
    *   **Resultado:** Un "Quickstart" de 30 segundos, documentación clara de los modos de ejecución (Mock vs Python) y troubleshooting.

2.  **Automatización de Pruebas (MockMvc)**
    *   **Acción:** Creación de `SentimentControllerMockMvcTest.java`.
    *   **Resultado:** 
        *   Test de éxito (200 OK) para asegurar que el flujo principal funciona.
        *   Test de fallo (400 Bad Request) para validar que el sistema rechaza inputs vacíos (robustez).

3.  **Contenedorización (Docker)**
    *   **Acción:** Creación del `Dockerfile` optimizado (Multi-stage build).
    *   **Resultado:** Imagen ligera basada en Alpine Linux que compila y ejecuta la app sin necesitar Maven instalado en el host.

4.  **Kit de Pruebas Manuales (Postman)**
    *   **Acción:** Generación de `Sentiment_Analysis.postman_collection.json`.
    *   **Mejoras:** 
        *   Uso de variable `{{baseUrl}}` para flexibilidad.
        *   Inclusión de casos de borde (texto vacío) y casos de negocio (positivo/negativo).
        *   Agregado endpoint `/health`.

### ⚠️ Problemas y Soluciones

| Problema / Desafío | Solución Implementada |
| :--- | :--- |
| **Legibilidad del README:** El archivo original era muy técnico y difícil de seguir para una demo rápida. | Se reestructuró priorizando los comandos de ejecución rápida y separando la configuración avanzada. |
| **Dependencia de Entorno:** Ejecutar tests manuales repetitivamente es propenso a errores. | Se implementaron tests unitarios de controlador (`MockMvc`) que se ejecutan con `./mvnw test`. |
| **Hardcoding en Postman:** Las URLs fijas complicaban probar si cambiaba el puerto o el host. | Se refactorizó la colección para usar variables de entorno. |

### 🔜 Próximos Pasos (Pendientes)

*   [ ] Ejecutar pruebas de integración completas una vez que Dev 1 conecte el servicio de Python real.
*   [ ] Validar el levantamiento del stack completo con `docker-compose` cuando el servicio de IA esté disponible.

---

## 🚀 Estado de Entrega (Rama: ToledoDev-QA)

**Estatus:** Listo para Merge Request (PR).

Se ha verificado que todos los artefactos (Código, Tests, Docker, Documentación) cumplen con los criterios de aceptación del rol Dev 4.

- **Código:** Comentado y estructurado (JavaDoc agregado).
- **Tests:** Unitarios (MockMvc) y Manuales (Postman) listos.
- **Docs:** README orientado al jurado y Bitácora actualizada.

## 📅 Sesión 2: Migración a JDK 17 y Estabilización de Build

### 🎯 Objetivos
Alinear el entorno de desarrollo con el estándar de despliegue (JDK 17) y asegurar que el proyecto compile y pase pruebas (`BUILD SUCCESS`).

### ✅ Tareas Realizadas

1.  **Downgrade de Java 21 a 17**
    *   **Acción:** Se modificó `pom.xml` (`<java.version>17</java.version>`) y `Dockerfile`.
    *   **Motivo:** Compatibilidad con el entorno de despliegue y herramientas del equipo.

2.  **Implementación de Componentes Base (Dev 3 Support)**
    *   **Acción:** Se crearon `SentimentRequest` (DTO), `SentimentResponse` (DTO con Enum `Prevision`) y `SentimentController` (Mock).
    *   **Motivo:** Necesarios para que los tests de integración compilen y ejecuten sin esperar al Dev 3.

3.  **Corrección de Errores de Compilación**
    *   **Acción:** Se solucionó el error de tipos incompatibles (`String` vs `Prevision`) en los DTOs.
    *   **Acción:** Se refactorizó `GlobalExceptionHandler` comentando código conflictivo para permitir la compilación y revisión posterior.

4.  **Ejecución Exitosa de Pruebas**
    *   **Resultado:** `./mvnw clean test` arroja **BUILD SUCCESS**.
    *   **Validación:** Se confirmó funcionamiento en entornos Windows (PowerShell) y Linux (Bash).

### ⚠️ Problemas y Soluciones

| Problema / Desafío | Solución Implementada |
| :--- | :--- |
| **Incompatibilidad JDK:** El proyecto estaba en Java 21 pero el equipo usa 17. | Se ajustó `pom.xml` y `maven-compiler-plugin` a release 17. |
| **Código Faltante:** Tests fallaban por falta de clases del Dev 3. | Se implementaron versiones Mock de `SentimentController` y DTOs. |
| **Errores de Sintaxis:** `GlobalExceptionHandler` tenía bloques mal cerrados. | Se limpió el archivo y se comentó el código problemático para Code Review. |

## 📅 Sesión 3: Integración, Resolución de Conflictos y Nuevos Tests

### 🎯 Objetivos
Integrar los avances de Dev 2 y Dev 3 (Main), resolver conflictos de fusión y ampliar la cobertura de pruebas para incluir escenarios de fallo y endpoints de salud.

### ✅ Tareas Realizadas

1.  **Sincronización con Rama Principal (Merge)**
    *   **Acción:** Se fusionaron los cambios de `origin/main` en la rama de QA.
    *   **Resolución:** Se aceptaron los cambios de lógica de negocio (Records para DTOs, validaciones avanzadas) y se adaptaron los tests existentes.

2.  **Adaptación de Pruebas (Refactor)**
    *   **Acción:** Actualización de `SentimentControllerMockMvcTest`.
    *   **Motivo:** El contrato de respuesta cambió de un POJO a un `record` con el campo `prevision` (antes `sentiment`).
    *   **Resultado:** Tests verdes nuevamente.

3.  **Pruebas de Escenarios de Fallo (Unhappy Path)**
    *   **Acción:** Implementación de tests con `@SpyBean` para simular caídas del servicio (Error 503).
    *   **Acción:** Tests para validaciones de longitud (Error 400 por texto muy corto/largo).

4.  **Endpoint de Salud (Health Check)**
    *   **Acción:** Creación de `HealthController` y `HealthControllerTest`.
    *   **Resultado:** Endpoint `/health` operativo y testeado, cumpliendo con los requisitos de monitoreo básico.

### ⚠️ Problemas y Soluciones

| Problema / Desafío | Solución Implementada |
| :--- | :--- |
| **Conflictos de Merge:** `pom.xml` y `GlobalExceptionHandler` tenían líneas conflictivas. | Se limpió el `pom.xml` eliminando duplicados y se aceptó la versión final del Handler de Dev 2. |
| **Cambio de Contrato:** Los tests fallaban porque el JSON de respuesta cambió. | Se actualizó `jsonPath("$.sentiment")` a `jsonPath("$.prevision")` en los tests. |