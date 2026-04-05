package com.demoblaze.automation.ui.targets;

import net.serenitybdd.screenplay.targets.Target;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  CheckoutModalTargets.java - Targets del Modal de Checkout           ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Define los selectores para el modal (ventana emergente) de checkout ║
 * ║  que aparece al hacer clic en "Place Order" en el carrito.           ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES UN MODAL?                                                   ║
 * ║  Es un cuadro de diálogo que aparece SOBRE la página actual          ║
 * ║  (no es una página nueva). Bloquea la interacción con el fondo       ║
 * ║  hasta que se cierre.                                                ║
 * ║                                                                      ║
 * ║  DESAFÍO TÉCNICO: Los modales en Selenium pueden ser complicados     ║
 * ║  porque necesitas esperar a que estén visibles antes de interactuar. ║
 * ║  Serenity maneja esto automáticamente con sus esperas implícitas.    ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: CompleteCheckoutForm (Task)                            ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
public class CheckoutModalTargets {

    /**
     * El contenedor principal del modal de checkout.
     * HTML: <div id="orderModal" class="modal fade" ...>
     *
     * Se usa para verificar que el modal está visible antes de interactuar.
     */
    public static final Target ORDER_MODAL =
        Target.the("modal de 'Place Order' (formulario de compra)")
              .locatedBy("#orderModal");

    /**
     * Campo "Name" en el formulario de checkout.
     * HTML: <input type="text" class="form-control" id="name">
     */
    public static final Target NAME_INPUT =
        Target.the("campo 'Name' en el formulario de compra")
              .locatedBy("#name");

    /**
     * Campo "Country" en el formulario de checkout.
     * HTML: <input type="text" class="form-control" id="country">
     */
    public static final Target COUNTRY_INPUT =
        Target.the("campo 'Country' en el formulario de compra")
              .locatedBy("#country");

    /**
     * Campo "City" en el formulario de checkout.
     * HTML: <input type="text" class="form-control" id="city">
     */
    public static final Target CITY_INPUT =
        Target.the("campo 'City' en el formulario de compra")
              .locatedBy("#city");

    /**
     * Campo "Credit card" en el formulario de checkout.
     * HTML: <input type="text" class="form-control" id="card">
     *
     * NOTA: DemoBlaze NO valida el número de tarjeta.
     * En un entorno real esto sería integrado con un proveedor de pagos.
     */
    public static final Target CREDIT_CARD_INPUT =
        Target.the("campo 'Credit card' en el formulario de compra")
              .locatedBy("#card");

    /**
     * Campo "Month" (mes de expiración de la tarjeta).
     * HTML: <input type="text" class="form-control" id="month">
     */
    public static final Target MONTH_INPUT =
        Target.the("campo 'Month' (mes de expiración) en el formulario de compra")
              .locatedBy("#month");

    /**
     * Campo "Year" (año de expiración de la tarjeta).
     * HTML: <input type="text" class="form-control" id="year">
     */
    public static final Target YEAR_INPUT =
        Target.the("campo 'Year' (año de expiración) en el formulario de compra")
              .locatedBy("#year");

    /**
     * Botón "Purchase" que finaliza la compra.
     * HTML: <button onclick="purchaseOrder()" class="btn btn-primary">Purchase</button>
     *
     * Este botón envía el formulario y muestra el modal de confirmación.
     */
    public static final Target PURCHASE_BUTTON =
        Target.the("botón 'Purchase' para confirmar la compra")
              .locatedBy("//button[text()='Purchase']");

    /**
     * Botón "Close" para cancelar el checkout.
     * Útil en escenarios negativos (cancelar antes de comprar).
     */
    public static final Target CLOSE_MODAL_BUTTON =
        Target.the("botón 'Close' para cancelar el checkout")
              .locatedBy("#orderModal .btn-secondary");
}
