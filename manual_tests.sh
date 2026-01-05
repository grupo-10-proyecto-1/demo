#!/bin/bash
# Script de pruebas manuales rápidas (QA)
# Ejecutar con: chmod +x manual_tests.sh && ./manual_tests.sh

BASE_URL="http://localhost:8080"

echo "--- Iniciando Pruebas Manuales (QA) ---"

echo "1. [GET] Health Check..."
curl -s "$BASE_URL/health" | grep "UP" && echo "   ✅ OK" || echo "   ❌ FAIL"

echo "2. [POST] Análisis de Sentimiento (Mock)..."
RESPONSE=$(curl -X POST "$BASE_URL/sentiment" \
     -H "Content-Type: application/json" \
     -d '{"text": "El equipo de desarrollo hizo un trabajo excelente"}' \
     -s)
echo "$RESPONSE" | grep "POSITIVO" && echo "   ✅ OK" || echo "   ❌ FAIL"

echo "3. [GET] Stats..."
curl -s "$BASE_URL/stats" | grep "total" && echo "   ✅ OK" || echo "   ❌ FAIL"

echo "4. [GET] History..."
curl -s "$BASE_URL/history" | grep "[" && echo "   ✅ OK" || echo "   ❌ FAIL"

echo "--- Fin de Pruebas ---"