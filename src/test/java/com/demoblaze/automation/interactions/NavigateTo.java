package com.demoblaze.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.core.annotations.findby.By;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  NavigateTo.java - Interaction: Navegación a URLs                    ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES UNA INTERACTION EN SCREENPLAY?                              ║
 * ║                                                                      ║
 * ║  Las Interactions son las acciones más ATÓMICAS (básicas) que un     ║
 * ║  actor puede realizar sobre la interfaz.                             ║
 * ║                                                                      ║
 * ║  JERARQUÍA DEL PATRÓN SCREENPLAY:                                    ║
 * ║  Actor → realiza → Tasks (acciones de negocio)                       ║
 * ║  Tasks → usan → Interactions (acciones de UI)                        ║
 * ║  Interactions → manipulan → Targets (elementos de la UI)             ║
 * ║                                                                      ║
 * ║  ANALOGÍA:                                                           ║
 * ║  - Task: "Agregar producto al carrito" (NEGOCIO)                     ║
 * ║  → usa → Interaction: NavigateTo.homePage() (TÉCNICO)                ║
 * ║  → usa → Interaction: ClickOn.target(ProductName) (TÉCNICO)          ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ SEPARAR INTERACTIONS DE TASKS?                             ║
 * ║  REUTILIZACIÓN: NavigateTo puede ser usada por MUCHAS Tasks           ║
 * ║  diferentes. Si la URL cambia, solo editas este archivo.             ║
 * ║                                                                      ║
 * ║  IMPLEMENTA: Interaction (interfaz de Screenplay)                    ║
 * ║  @Subject: Texto que aparecerá en el reporte de Serenity             ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("navega a #page")  // #page será reemplazado por el valor del campo 'page'
public class NavigateTo implements Interaction {

    // ─────────────────────────────────────────────────────────────────────
    //  CONSTANTES DE URL
    //  Centralizadas aquí para evitar duplicación (principio DRY).
    //  Si cambia la URL, la cambias en un solo lugar.
    // ─────────────────────────────────────────────────────────────────────
    private static final String BASE_URL = "https://www.demoblaze.com";
    private static final String CART_URL  = BASE_URL + "/cart.html";

    /** La URL o nombre de página a la que navegar */
    private final String page;

    // ─────────────────────────────────────────────────────────────────────
    //  CONSTRUCTOR PRIVADO
    //  Solo accesible a través de los métodos de fábrica estáticos.
    //  Esto garantiza un API limpio y legible.
    // ─────────────────────────────────────────────────────────────────────
    private NavigateTo(String page) {
        this.page = page;
    }

    // ─────────────────────────────────────────────────────────────────────
    //  MÉTODOS DE FÁBRICA ESTÁTICOS (Static Factory Methods)
    //  API fluido para crear instancias de esta Interaction.
    //
    //  USO en una Task:
    //  actor.attemptsTo(NavigateTo.theHomePage());
    //  actor.attemptsTo(NavigateTo.theCartPage());
    //
    //  ¿Por qué no usar 'new NavigateTo("https://...")'?
    //  Los métodos de fábrica tienen nombres descriptivos que documentan
    //  el código sin necesidad de comentarios adicionales.
    // ─────────────────────────────────────────────────────────────────────

    /** Navega a la página principal de DemoBlaze */
    public static NavigateTo theHomePage() {
        return new NavigateTo(BASE_URL);
    }

    /** Navega directamente al carrito de compras */
    public static NavigateTo theCartPage() {
        return new NavigateTo(CART_URL);
    }

    /** Navega a cualquier URL específica (para casos adicionales) */
    public static NavigateTo thePageAt(String url) {
        return new NavigateTo(url);
    }

    // ─────────────────────────────────────────────────────────────────────
    //  performAs() - El método principal de todas las Interactions
    //
    //  Este método es llamado por Serenity cuando el actor "intenta"
    //  realizar esta interacción.
    //
    //  El parámetro 'actor' da acceso a las capacidades del actor
    //  (como el WebDriver para controlar el navegador).
    //
    //  Open.url() es una Interaction incorporada de Serenity que
    //  llama a driver.get(url) internamente.
    // ─────────────────────────────────────────────────────────────────────
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Open.url(page)  // Equivalente a driver.get(page) pero con reporte
        );
    }
}
