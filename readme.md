================================================================================
  DemoBlaze E2E — Serenity BDD + Screenplay + Cucumber
  Instrucciones paso a paso de ejecución
================================================================================

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
        git clone <URL_DEL_REPOSITORIO>
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

7. Subir a GitHub (repositorio público)
   -------------------------------------
   a) Crear un repositorio nuevo en GitHub y marcarlo como público.
   b) En la raíz del proyecto (si aún no hay git):
        git init
        git add .
        git commit -m "Entrega: framework Serenity BDD DemoBlaze"
   c) Conectar el remoto y subir (sustituya USUARIO y REPO):
        git remote add origin https://github.com/USUARIO/REPO.git
        git branch -M main
        git push -u origin main

8. Qué debe contener el repositorio para reproducibilidad
   -------------------------------------------------------
   - pom.xml y código fuente bajo src/
   - Características Gherkin en src/test/resources/features/
   - serenity.conf y configuración de pruebas en src/test/resources/
   - Este readme.txt y conclusiones.txt
   - scripts/ (ejecutar-pruebas.sh, copiar-reportes.sh)
   - Opcional pero recomendable si lo piden explícitamente: carpeta reportes/
     generada con el script del paso 6

9. Problemas frecuentes
   ---------------------
   - Timeout o fallos intermitentes: DemoBlaze es un sitio demo en red;
     reintente o compruebe su conexión.
   - Versión de Java incorrecta: el proyecto está configurado para Java 11
     en pom.xml; use JDK 11+.
   - Chrome desactualizado: actualice Chrome o deje que WebDriverManager
     resuelva la versión del driver compatible.

