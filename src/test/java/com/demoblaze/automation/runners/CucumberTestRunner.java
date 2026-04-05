package com.demoblaze.automation.runners;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  CucumberTestRunner.java - El "Director de Orquesta" del proyecto    ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Es el punto de entrada que JUnit usa para ejecutar TODOS los tests. ║
 * ║  Sin este archivo, Maven no sabría cómo lanzar las pruebas.          ║
 * ║                                                                      ║
 * ║  ¿CÓMO FUNCIONA?                                                     ║
 * ║  1. Maven ejecuta las clases que terminan en *Runner.class           ║
 * ║  2. JUnit ve @RunWith → delega al runner especificado                ║
 * ║  3. CucumberWithSerenity busca los @FeaturePath y ejecuta los tests   ║
 * ║  4. @CucumberOptions configura cómo se ejecutan:                     ║
 * ║     - Dónde están los .feature files                                  ║
 * ║     - Dónde están los Step Definitions                               ║
 * ║     - Qué plugins de reporte usar                                    ║
 * ║     - Qué tags ejecutar (subset de pruebas)                          ║
 * ║                                                                      ║
 * ║  @RunWith(CucumberWithSerenity.class):                               ║
 * ║  - CucumberWithSerenity: versión de Cucumber integrada con Serenity  ║
 * ║  - Agrega captura de screenshots, reportes Serenity y Screenplay     ║
 * ║                                                                      ║
 * ║  ANALOGÍA: Es el "main()" de las pruebas de integración.             ║
 * ║  Todo empieza aquí.                                                  ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@RunWith(CucumberWithSerenity.class)  // [1] Usar el runner de Serenity (no el de Cucumber estándar)
@CucumberOptions(
    // [2] Dónde están los archivos .feature (relativo al classpath)
    // classpath = src/test/resources/
    features = "src/test/resources/features",

    // [3] Paquete(s) donde Cucumber buscará los @Dado/@Cuando/@Entonces
    glue = {
        "com.demoblaze.automation.stepdefinitions"
        // Agregar más paquetes si adds si tienes steps en otros paquetes:
        // "com.demoblaze.automation.hooks"
    },

    // [4] Plugins de reporte
    // "pretty" → formatea la salida en consola de forma elegante
    // "html:target/cucumber-reports/..." → reporte HTML básico de Cucumber
    //  (Serenity genera su propio reporte más completo en target/site/serenity/)
    plugin = {
        "pretty",
        "html:target/cucumber-reports/cucumber-html-report.html",
        "json:target/cucumber-reports/cucumber.json"
    },

    // [5] Tags para filtrar qué escenarios ejecutar
    // Vacío = ejecuta TODOS los escenarios
    // "@CompraExitosa" = solo ejecuta escenarios con ese tag
    // "~@Ignorar" = excluye escenarios con tag @Ignorar
    tags = "",  // Ejecuta todos los escenarios

    // [6] Mostrar tipos de Steps con sus regex asociadas
    // true → muestra el mapeo entre Gherkin y código Java (útil para debug)
    stepNotifications = true

    // [7] Deshabilitar ejecución automática de escenarios sin Step Definition
    // monochrome = true → salida sin colores (para logs simples)
    // dryRun = true → valida que todos los steps están definidos sin ejecutar
)
public class CucumberTestRunner {
    /*
     * Esta clase NO necesita métodos.
     * JUnit y Cucumber usan las anotaciones para configurarlo todo.
     * El cuerpo vacío es correcto y es el estándar en la industria.
     *
     * EJECUCIÓN:
     * 1. Desde Maven:  mvn verify
     * 2. Desde IntelliJ: Clic derecho → "Run CucumberTestRunner"
     * 3. Solo un tag:  mvn verify -Dcucumber.filter.tags="@CompraExitosa"
     */
}
