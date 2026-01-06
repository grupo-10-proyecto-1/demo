# 🧪 Guía de Pruebas y Calidad (QA)

Este documento detalla los procedimientos de prueba para validar el Backend de Análisis de Sentimientos.

## 1. Pruebas Unitarias (Automáticas)

Ejecutamos tests de integración con `MockMvc` para validar los controladores, la lógica de negocio y el manejo de excepciones sin levantar el servidor completo.

**Cobertura (8 Pruebas):**
1.  **Contexto Spring**: Carga correcta de la aplicación y configuración.
2.  **Health Check**: Disponibilidad del servicio (`/health`).
3.  **Análisis Exitoso**: Flujo completo con respuesta JSON válida (`/sentiment`).
4.  **Validación Vacío**: Error 400 ante input vacío.
5.  **Validación Longitud**: Error 400 ante texto muy corto.
6.  **Health Modelo**: Verificación de estado del modelo IA (`/health/model`).
7.  **Estadísticas**: Cálculo y respuesta de stats (`/stats`).
8.  **Historial**: Recuperación de registros persistidos (`/history`).

**Comando para ejecutar:**
```bash
./mvnw test
```
*Resultado esperado: `BUILD SUCCESS` con 0 fallos.*

---

## 2. Smoke Tests (Script Rápido)

Hemos creado un script de "Humo" para verificar en 1 segundo que la API responde correctamente en un entorno tipo Linux/Mac/WSL.

**Prerrequisito:** La aplicación debe estar corriendo (`./mvnw spring-boot:run`).

**Comando:**
```bash
chmod +x manual_tests.sh
./manual_tests.sh
```

**Salida esperada:**
```text
1. [GET] Health Check...   ✅ OK
2. [POST] Análisis...      ✅ OK
3. [GET] Stats...          ✅ OK
4. [GET] History...        ✅ OK
```

---

## 3. Pruebas Manuales (Postman)

Para una inspección visual y detallada de los JSONs de respuesta.

1. Importar el archivo `Sentiment_Analysis.postman_collection.json` en Postman.
2. Asegurarse de que la variable `{{baseUrl}}` sea `http://localhost:8080`.
3. Ejecutar la colección completa o endpoints individuales.