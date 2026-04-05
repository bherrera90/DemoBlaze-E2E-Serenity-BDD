package com.demoblaze.automation.tasks;

import com.demoblaze.automation.interactions.ClickOn;
import com.demoblaze.automation.interactions.NavigateTo;
import com.demoblaze.automation.ui.targets.HomePageTargets;
import com.demoblaze.automation.ui.targets.ProductPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Browser;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  AddProductToCart.java - Task: Agregar un Producto al Carrito        ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES UNA TASK EN SCREENPLAY?                                     ║
 * ║                                                                      ║
 * ║  Las Tasks representan ACCIONES DE NEGOCIO de alto nivel que un      ║
 * ║  actor puede realizar. Son secuencias de Interactions que juntas     ║
 * ║  logran un objetivo de negocio.                                      ║
 * ║                                                                      ║
 * ║  DIFERENCIA con Interactions:                                        ║
 * ║  - Interaction: "hacer clic en el botón" (técnico, atómico)          ║
 * ║  - Task: "agregar producto al carrito" (negocio, compuesto)          ║
 * ║                                                                      ║
 * ║  ESTA TASK ejecuta los siguientes pasos:                             ║
 * ║  1. Navegar a la página principal                                    ║
 * ║  2. Esperar que los productos carguen (sin Thread.sleep!)            ║
 * ║  3. Hacer clic en el nombre del producto                             ║
 * ║  4. Esperar que la página de detalle cargue                          ║
 * ║  5. Hacer clic en "Add to cart"                                      ║
 * ║  6. Manejar el alert de confirmación de JavaScript                   ║
 * ║  7. Regresar a la página de inicio para el siguiente producto        ║
 * ║                                                                      ║
 * ║  ESPERAS EXPLÍCITAS: Usa WaitUntil.the() de Serenity.                ║
 * ║  NUNCA usa Thread.sleep() porque detiene el hilo completo.           ║
 * ║  WaitUntil sondea el DOM activamente hasta que la condición           ║
 * ║  se cumpla o se alcance el timeout configurado en serenity.conf.     ║
 * ║                                                                      ║
 * ║  @Subject: Texto que aparece en el reporte de Serenity.              ║
 * ║  '#productName' → valor del campo productName                        ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usa: NavigateTo, ClickOn (Interactions)                           ║
 * ║  - Usa: HomePageTargets, ProductPageTargets (Targets)                ║
 * ║  - Llamada desde: PurchaseFlowStepDefinitions (Step Defs)            ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("agrega el producto '#productName' al carrito")
public class AddProductToCart implements Task {

    /** El nombre del producto a agregar (ej: "Samsung galaxy s6") */
    private final String productName;
    
    // LOGGER: Trazabilidad empresarial para Debugging
    private static final Logger LOGGER = LoggerFactory.getLogger(AddProductToCart.class);

    // Constructor privado → uso a través de método de fábrica
    private AddProductToCart(String productName) {
        this.productName = productName;
    }

    /**
     * Método de fábrica estático para crear la Task.
     *
     * USO EN STEP DEFINITIONS:
     * actor.attemptsTo(AddProductToCart.named("Samsung galaxy s6"));
     *
     * Esta convención (named/withName/called) es un estándar en Screenplay
     * que hace el código legible como prosa en inglés.
     */
    public static AddProductToCart named(String productName) {
        return new AddProductToCart(productName);
    }

    /**
     * performAs() - Orquesta la secuencia de pasos para agregar el producto.
     *
     * actor.attemptsTo() → Ejecuta una o más Interactions/Tasks en secuencia.
     * Si alguna falla, Serenity reporta el fallo y toma un screenshot.
     */
    @Override
    public <T extends Actor> void performAs(T actor) {
        LOGGER.info("Iniciando agregado de producto al carrito: {}", productName);
        
        try {
            LOGGER.debug("Esperando que cargue el contenedor de productos y clickeando el producto: {}", productName);
            actor.attemptsTo(
                WaitUntil.the(com.demoblaze.automation.ui.targets.HomePageTargets.PRODUCTS_CONTAINER, isVisible())
                         .forNoMoreThan(15).seconds(),
                WaitUntil.the(
                    com.demoblaze.automation.ui.targets.HomePageTargets.PRODUCT_LINK_BY_NAME.of(productName), isVisible()
                ).forNoMoreThan(10).seconds(),
                com.demoblaze.automation.interactions.ClickOn.targetUsingJavaScript(com.demoblaze.automation.ui.targets.HomePageTargets.PRODUCT_LINK_BY_NAME.of(productName))
            );

            LOGGER.debug("Esperando el botón Add To Cart y clickando");
            actor.attemptsTo(
                WaitUntil.the(ProductPageTargets.ADD_TO_CART_BUTTON, isVisible())
                         .forNoMoreThan(10).seconds(),
                com.demoblaze.automation.interactions.ClickOn.targetUsingJavaScript(ProductPageTargets.ADD_TO_CART_BUTTON)
            );

            LOGGER.debug("Esperando alert y volviendo a la página de inicio");
            actor.attemptsTo(
                new net.serenitybdd.screenplay.Interaction() {
                    @Override
                    public <T extends Actor> void performAs(T act) {
                        try {
                            org.openqa.selenium.WebDriver driver = net.serenitybdd.screenplay.abilities.BrowseTheWeb.as(act).getDriver();
                            org.openqa.selenium.support.ui.WebDriverWait wait =
                                    new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
                            org.openqa.selenium.Alert alert = wait.until(org.openqa.selenium.support.ui.ExpectedConditions.alertIsPresent());
                            LOGGER.info("Alerta interceptada adecuadamente");
                            alert.accept();
                        } catch (org.openqa.selenium.TimeoutException e) {
                            LOGGER.error("Timeout esperando la alerta", e);
                            throw e;
                        }
                    }
                },
                com.demoblaze.automation.interactions.NavigateTo.theHomePage()
            );
            
            LOGGER.info("Producto {} agregado al carrito satisfactoriamente", productName);
        } catch (Exception ex) {
            LOGGER.error("Error crítico agregando producto al carrito", ex);
            throw ex;
        }
    }
}
