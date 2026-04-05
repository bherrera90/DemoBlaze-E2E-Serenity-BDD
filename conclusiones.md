# 📈 Informe de hallazgos y conclusiones

**Proyecto:** DemoBlaze E2E – Serenity BDD + Screenplay + Cucumber  
**Tipo:** Automatización de pruebas end‑to‑end  
**Entorno:** Local (Java 11+, Maven, Chrome)  

---

## 🎯 Resumen 

> Se automatizó exitosamente el flujo de compra completo del sitio DemoBlaze, implementando **BDD con Gherkin**, **Screenplay Pattern** y **Serenity BDD**.  
> La solución es **reproducible**, **mantenible** y genera **reportes vivos** comprensibles para negocio y equipo técnico.  
> **CI:** GitHub Actions (workflow `e2e-demoblaze.yml`) ejecuta el mismo flujo `mvn clean verify` en Ubuntu con Chrome headless y adjunta Serenity + Cucumber como artefactos de cada ejecución.

| Indicador clave | Resultado |
|----------------|-----------|
| Escenarios BDD | 1 flujo principal (compra completa) |
| Reporte Serenity | `target/site/serenity/index.html` |

---

## 🧠 Hallazgos técnicos

| Área | Hallazgo | Impacto |
|------|----------|---------|
| **Screenplay Pattern** | Separación clara en `Tasks`, `Interactions`, `Questions` y `Targets`. | Alta mantenibilidad; los cambios en selectores UI no afectan los escenarios Gherkin. |
| **Serenity BDD + Cucumber** | Integración nativa con reportes *Living Documentation*. | Trazabilidad total entre requisitos (features) y código de prueba. |
| **WebDriverManager** | Gestión automática del ChromeDriver. | Reproducibilidad inmediata en cualquier máquina, sin drivers manuales. |
| **Manejo de modales / latencia** | Necesidad de esperas explícitas y condiciones Serenity (`waitFor`). | Escenario robusto frente a la latencia variable del sitio demo. |

---

## 🌐 Hallazgos sobre la aplicación (DemoBlaze)

- **Disponibilidad:** Sitio público real, depende de red externa → posibilidad de *timeouts* ocasionales.
- **Interfaz:** Modales y navegación por categorías exigen sincronización dinámica.
- **Ventaja:** Ideal para prácticas E2E; se comporta como una e‑commerce típica.

---

## ✅ Resultado de la última ejecución de referencia
mvn clean verify

| Reporte | Estado | Ubicación |
|---------|--------|------------|
| Serenity Summary | 1 caso, 1 pasado | `target/site/serenity/summary.txt` |
| Cucumber HTML | 0 fallos | `target/cucumber-reports/cucumber-html-report.html` |



---

## 💎 Conclusiones estratégicas

1. **BDD + Screenplay + Serenity** es una combinación excelente para proyectos donde la documentación ejecutable y la comunicación negocio‑técnica son críticas.
2. El framework creado es **altamente reproducible** gracias a Maven, WebDriverManager y scripts de ejecución.
3. Dependencia del entorno externo: aunque la reproducibilidad técnica es alta, el sitio DemoBlaze puede variar o estar lento, causando falsos fallos.

---

## 🚀 Mejoras futuras (roadmap)

| Prioridad | Mejora | Beneficio |
|-----------|--------|------------|
| 🔴 Alta | Añadir escenarios negativos (carrito vacío, datos inválidos, cancelación) | Mayor cobertura de riesgos. |
| 🟡 Media | Parametrización de datos con archivos externos (`.properties` o variables de entorno) | Separar datos de lógica, preparar para CI. |
| ✅ Hecho | Integración con GitHub Actions | `.github/workflows/e2e-demoblaze.yml`: push/PR a `main` o `master`, ejecución manual y *cron* semanal; artefactos Serenity + Cucumber. |

---

## 📊 Diagrama del flujo automatizado (Mermaid)

El siguiente diagrama representa el escenario BDD ejecutado:

```mermaid
graph TD
    A[Usuario abre DemoBlaze] --> B[Selecciona un producto]
    B --> C[Añade producto al carrito]
    C --> D[Acepta alerta de confirmación]
    D --> E[Abre el carrito de compra]
    E --> F[Realiza pedido: completa formulario]
    F --> G[Confirma compra]
    G --> H[Verifica mensaje de éxito y ID de orden]