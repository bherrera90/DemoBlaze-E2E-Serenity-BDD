#!/usr/bin/env bash
# Copia reportes generados por Maven a reportes/ para poder versionarlos en git.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT"
DEST="$ROOT/reportes"
mkdir -p "$DEST"

if [[ -d "$ROOT/target/site/serenity" ]]; then
  rm -rf "$DEST/serenity"
  cp -R "$ROOT/target/site/serenity" "$DEST/serenity"
  echo "Copiado: $DEST/serenity (abrir index.html)"
else
  echo "No existe target/site/serenity. Ejecute antes: mvn clean verify" >&2
  exit 1
fi

if [[ -f "$ROOT/target/cucumber-reports/cucumber-html-report.html" ]]; then
  mkdir -p "$DEST/cucumber"
  cp "$ROOT/target/cucumber-reports/cucumber-html-report.html" "$DEST/cucumber/"
  echo "Copiado: $DEST/cucumber/cucumber-html-report.html"
fi
