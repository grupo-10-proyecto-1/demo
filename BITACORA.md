# Bitácora de Desarrollo - Dev 4 (QA/Testing)

## Sesión: Estabilización de Pruebas y Migración a JDK 17

### Contexto
El desarrollo inicial se realizó en un entorno local con **JDK 21**, pero el estándar del equipo y del despliegue es **JDK 17**. Esto generaba riesgos de incompatibilidad y errores en el pipeline de CI/CD. Además, faltaban las implementaciones base para que las pruebas automáticas funcionaran.

### Acciones Realizadas

#### 1. Unificación de Versión Java (JDK 17)
- **Archivo modificado**: `pom.xml`
- **Cambio**: Se actualizó la propiedad `<java.version>` de `21` a `17`.
- **Cambio**: Se configuró el `maven-compiler-plugin` con `<release>17</release>`.
- **Ajuste Framework**: Se estableció la versión de Spring Boot en `3.2.5` (estable y compatible) para reemplazar la versión inexistente `3.5.0`.

#### 2. Implementación de Pruebas (QA)
- **Dependencias**: Se agregó `spring-boot-starter-test` y `spring-boot-starter-validation`.
- **Tests**: Se creó `SentimentControllerMockMvcTest` para validar:
  - Respuesta 200 OK con estructura JSON correcta (Happy Path).
  - Respuesta 400 Bad Request ante inputs vacíos (Validación).

#### 3. Desarrollo de Componentes Base (Soporte a Tests)
Para que los tests pudieran compilar y ejecutar (evitando falsos positivos), se implementaron los artefactos mínimos del **Dev 3**:
- **DTOs**: `SentimentRequest` (con validación `@NotBlank`) y `SentimentResponse`.
- **Enum**: `Prevision` para manejo tipado de "Positivo", "Negativo", "Neutro".
- **Controller**: `SentimentController` con lógica Mock.

#### 4. Resolución de Conflictos de Compilación
- **Error**: `Incompatible types: Prevision cannot be converted to String`.
- **Solución**: Se refactorizó `SentimentResponse` y `SentimentController` para usar estrictamente el Enum `Prevision`, eliminando el uso de Strings "quemados" y garantizando seguridad de tipos.

#### 5. Manejo de Errores (Robustez)
- **Archivo**: `GlobalExceptionHandler.java`
- **Acción**: Se estructuró el manejo de excepciones para `MethodArgumentNotValidException` (400) y `Exception` (500).
- **Corrección**: Se comentó código duplicado o con errores de sintaxis (llaves mal cerradas) que impedía la compilación, dejando las secciones problemáticas marcadas para revisión por pares (Code Review).

### Estado Final
- **Comando**: `./mvnw clean test`
- **Resultado**: `BUILD SUCCESS`
- **Entorno**: Compatible 100% con JDK 17 (Validado en Windows y Linux).