#!/usr/bin/env bash
# Ejecuta la suite E2E con Maven (Serenity + Cucumber).
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"
echo "Directorio del proyecto: $ROOT"
echo "Ejecutando: mvn clean verify"
mvn clean verify
echo "Listo. Reporte Serenity: target/site/serenity/index.html"
