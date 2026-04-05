package com.demoblaze.automation.tasks;

import com.demoblaze.automation.ui.targets.CartPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  ViewCart.java - Task: Navegar y Visualizar el Carrito               ║
 * ║                                                                      ║
 * ║  ¿QUÉ HACE ESTA TASK?                                                ║
 * ║  1. Hace clic en el enlace "Cart" de la navegación                   ║
 * ║  2. Espera que la página del carrito cargue completamente            ║
 * ║  3. Espera que los productos del carrito aparezcan en la tabla       ║
 * ║                                                                      ║
 * ║  DESAFÍO TÉCNICO:                                                    ║
 * ║  El carrito también carga productos vía AJAX.                        ║
 * ║  Si le preguntas inmediatamente "¿cuántos productos hay?",           ║
 * ║  la respuesta puede ser "0" porque aún no han cargado.               ║
 * ║  La espera explícita resuelve esto.                                  ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usa: NavigateTo, ClickOn (Interactions)                           ║
 * ║  - Usa: HomePageTargets, CartPageTargets (Targets)                   ║
 * ║  - Llamada desde: PurchaseFlowStepDefinitions                        ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("navega al carrito de compras y espera que los productos carguen")
public class ViewCart implements Task {

    public ViewCart() {
        // Constructor público necesario para InstrumentedTask proxy
    }

    /** API fluido: actor.attemptsTo(ViewCart.products()); */
    public static ViewCart products() {
        return net.serenitybdd.screenplay.Tasks.instrumented(ViewCart.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        // ─────────────────────────────────────────────────────────────────
        //  PASO 1: Navegar directamente a la URL del carrito
        //  Más confiable que hacer clic en el enlace del navbar,
        //  ya que el enlace puede ser ambiguo o estar en transición.
        // ─────────────────────────────────────────────────────────────────
        actor.attemptsTo(
            com.demoblaze.automation.interactions.NavigateTo.theCartPage()
        );

        // ─────────────────────────────────────────────────────────────────
        //  PASO 2: Esperar que la tabla del carrito exista en el DOM.
        //  'isPresent()' verifica que el elemento existe en HTML.
        //  'isVisible()' verifica que además es visible (no display:none).
        //  Usamos isPresent() primero porque el tbody puede existir pero vacío.
        // ─────────────────────────────────────────────────────────────────
        actor.attemptsTo(
            WaitUntil.the(CartPageTargets.CART_TABLE_BODY, isPresent())
                     .forNoMoreThan(15).seconds()
        );

        // ─────────────────────────────────────────────────────────────────
        //  PASO 3: Esperar que aparezcan filas de productos en el carrito.
        //  Las filas se cargan asíncronamente.
        //  'isCurrentlyVisible()' es una verificación inmediata (no polling).
        //  'isVisible()' hace polling → más robusto para esperas.
        // ─────────────────────────────────────────────────────────────────
        actor.attemptsTo(
            WaitUntil.the(CartPageTargets.CART_PRODUCT_ROWS, isVisible())
                     .forNoMoreThan(15).seconds()
        );
    }
}
