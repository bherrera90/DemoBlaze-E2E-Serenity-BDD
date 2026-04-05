package com.demoblaze.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.JavaScriptClick;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.targets.Target;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  ClickOn.java - Interaction: Hacer clic en un Target                 ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTA INTERACTION?                                           ║
 * ║  Es una abstracción sobre el clic de Selenium que:                   ║
 * ║  1. Hace scroll hasta el elemento antes de hacer clic                ║
 * ║  2. Maneja elementos que solo son clickeables vía JavaScript         ║
 * ║  3. Aparece con nombre descriptivo en los reportes de Serenity       ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ NO USAR Click.on() DIRECTAMENTE EN LAS TASKS?             ║
 * ║  Click.on() funciona bien, pero esta clase agrega:                   ║
 * ║  - Alternativa via JavaScript (para elementos bloqueados)            ║
 * ║  - Un punto central de control si necesitamos cambiar comportamiento ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: AddProductToCart, ViewCart, ConfirmPurchase (Tasks)    ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("hace clic en '#target'")
public class ClickOn implements Interaction {

    private final Target target;
    private final boolean useJavaScript;

    private ClickOn(Target target, boolean useJavaScript) {
        this.target = target;
        this.useJavaScript = useJavaScript;
    }

    /**
     * Clic estándar de Selenium en el target.
     * Usar en la mayoría de los casos.
     *
     * EJEMPLO:
     * actor.attemptsTo(ClickOn.target(HomePageTargets.CART_LINK));
     */
    public static ClickOn target(Target target) {
        return new ClickOn(target, false);
    }

    /**
     * Clic via JavaScript en el target.
     * Usar cuando el clic estándar falla porque:
     * - El elemento está oculto bajo otro elemento
     * - Hay un overlay/popup bloqueando el clic
     * - El elemento no es interactivo pero necesita ser activado
     *
     * EJEMPLO:
     * actor.attemptsTo(ClickOn.targetUsingJavaScript(ProductPageTargets.ADD_TO_CART_BUTTON));
     */
    public static ClickOn targetUsingJavaScript(Target target) {
        return new ClickOn(target, true);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        if (useJavaScript) {
            // JavaScriptClick bypasses Selenium's normal click mechanics
            // Útil cuando hay problemas de interceptación de clics
            actor.attemptsTo(
                JavaScriptClick.on(target)
            );
        } else {
            // Click.on() es la forma estándar en Screenplay de hacer clic
            // Equivale a driver.findElement(selector).click()
            actor.attemptsTo(
                Click.on(target)
            );
        }
    }
}
