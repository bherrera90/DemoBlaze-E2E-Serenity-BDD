# DemoBlaze E2E — Serenity BDD + Screenplay + Cucumber

Instrucciones paso a paso de ejecución.

[![E2E · DemoBlaze (Serenity BDD)](https://github.com/TU_USUARIO/TU_REPO/actions/workflows/e2e-demoblaze.yml/badge.svg)](https://github.com/Bherrera90/DemoBlaze-E2E-Serenidad-BDD/actions/workflows/e2e-demoblaze.yml)

Sustituye `Bherrera90` y `DemoBlaze-E2E-Serenidad-BDD` en la URL del *badge* por tu organización o usuario y el nombre del repositorio en GitHub (o elimina la línea del badge si aún no has publicado el repo).

1. Requisitos previos
   -------------------
   a) Java JDK 11 o superior instalado.
      Comprobar:  java -version
   b) Apache Maven 3.6+ instalado.
      Comprobar:  mvn -version
   c) Google Chrome instalado (el proyecto usa WebDriverManager para el driver).
   d) Conexión a Internet (DemoBlaze es https://www.demoblaze.com y Maven descarga dependencias).

2. Obtener el código
   --------------------
   a) Clonar el repositorio público de GitHub (sustituya la URL por la de su repo):
        git clone <https://github.com/bherrera90/DemoBlaze-E2E-Serenity-BDD.git>
   b) Entrar al directorio del proyecto:
        cd ejercicio1-serenity-bdd

3. Ejecutar las pruebas (opción A — Maven directo)
   -------------------------------------------------
   a) Desde la raíz del proyecto (donde está pom.xml):
        mvn clean verify
   b) Esperar a que termine la fase de integración (failsafe). La primera vez
      puede tardar más por la descarga de dependencias y del ChromeDriver.

4. Ejecutar las pruebas (opción B — script, Linux / macOS)
   --------------------------------------------------------
   a) Dar permisos de ejecución (solo la primera vez):
        chmod +x scripts/ejecutar-pruebas.sh
   b) Ejecutar:
        ./scripts/ejecutar-pruebas.sh

5. Ver los reportes generados
   ---------------------------
   Tras una ejecución exitosa de "mvn clean verify":
   a) Reporte Serenity (Living documentation):
        Abrir en el navegador el archivo:
        target/site/serenity/index.html
   b) Reporte Cucumber HTML (si se generó en su configuración):
        target/cucumber-reports/cucumber-html-report.html

6. Incluir reportes en el repositorio (si la entrega lo exige)
   ------------------------------------------------------------
   La carpeta "target/" no se versiona (es generada). Para dejar una copia
   de los reportes dentro del repo para quien revise sin ejecutar:
   a) Tras "mvn clean verify" exitoso:
        chmod +x scripts/copiar-reportes.sh
        ./scripts/copiar-reportes.sh
   b) Revisar que exista la carpeta reportes/serenity (y opcionalmente
      reportes/cucumber si el script la copió).
   c) Hacer commit de la carpeta reportes/ según las indicaciones de su curso.


7. Qué debe contener el repositorio para reproducibilidad
   -------------------------------------------------------
   - pom.xml y código fuente bajo src/
   - Características Gherkin en src/test/resources/features/
   - serenity.conf y configuración de pruebas en src/test/resources/
   - `readme.md` y `conclusiones.md`
   - scripts/ (ejecutar-pruebas.sh, copiar-reportes.sh)
   - Opcional pero recomendable si lo piden explícitamente: carpeta reportes/
     generada con el script del paso 6
   - `.github/workflows/e2e-demoblaze.yml` (CI en GitHub Actions)

8. CI con GitHub Actions
   ----------------------
   El repositorio incluye un workflow que ejecuta `mvn clean verify` en **Ubuntu**
   con **Chrome en modo headless** (ya configurado en `serenity.conf`).

   a) **Cuándo se ejecuta:** al hacer *push* o abrir/actualizar un *pull request*
      hacia `main` o `master`, manualmente (*Actions* → *E2E · DemoBlaze* → *Run workflow*)
      y de forma programada (lunes ~07:00 UTC, *smoke* semanal).
   b) **Resultados:** en la pestaña *Actions* de GitHub; cada ejecución publica
      artefactos descargables con el reporte Serenity y los reportes Cucumber.
   c) **Requisito:** el código debe estar en GitHub; el *badge* del inicio de este
      archivo solo mostrará estado correcto tras sustituir `Bherrera90`/`DemoBlaze-E2E-Serenidad-BDD`.

9. Problemas frecuentes
   ---------------------
   - Timeout o fallos intermitentes: DemoBlaze es un sitio demo en red;
     reintente o compruebe su conexión.
   - Versión de Java incorrecta: el proyecto está configurado para Java 11
     en pom.xml; use JDK 11+.
   - Chrome desactualizado: actualice Chrome o deje que WebDriverManager
     resuelva la versión del driver compatible.

