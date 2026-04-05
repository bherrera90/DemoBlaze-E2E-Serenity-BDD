package com.demoblaze.automation.ui.targets;

import net.serenitybdd.screenplay.targets.Target;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  CartPageTargets.java - Targets de la Página del Carrito            ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Define los selectores para la página del carrito de compras.        ║
 * ║  URL: https://www.demoblaze.com/cart.html                            ║
 * ║                                                                      ║
 * ║  ELEMENTOS CLAVE DEL CARRITO:                                        ║
 * ║  - Tabla con los productos agregados                                 ║
 * ║  - Precio total                                                      ║
 * ║  - Botón "Place Order" para ir al checkout                           ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: ViewCart (Task), CompleteCheckoutForm (Task)           ║
 * ║  - Usado por: CartContents (Question)                                ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
public class CartPageTargets {

    /**
     * Cuerpo de la tabla del carrito donde aparecen los productos.
     *
     * HTML: <tbody id="tbodyid">...</tbody>
     * Dentro hay filas <tr> con los productos.
     *
     * Usamos #tbodyid para esperar que los productos hayan cargado.
     * (Los productos se cargan vía AJAX, hay que esperar)
     */
    public static final Target CART_TABLE_BODY =
        Target.the("cuerpo de la tabla de productos en el carrito")
              .locatedBy("#tbodyid");

    /**
     * Filas de productos en la tabla del carrito.
     * Cada <tr> representa un producto agregado.
     * Usamos XPath para obtener todas las filas hijas de tbody.
     *
     * #tbodyid tr → todos los <tr> dentro del tbody con id="tbodyid"
     */
    public static final Target CART_PRODUCT_ROWS =
        Target.the("filas de productos en el carrito")
              .locatedBy("#tbodyid tr");

    /**
     * Nombre de un producto específico dentro del carrito.
     * {0} → reemplazado por el nombre del producto.
     *
     * XPath: //td[text()='{0}']
     * → cualquier celda <td> cuyo texto exacto sea el nombre buscado
     */
    public static final Target CART_PRODUCT_NAME =
        Target.the("producto '{0}' en el carrito")
              .locatedBy("//td[normalize-space(text())='{0}']");

    /**
     * Precio total mostrado al final del carrito.
     * HTML: <h3 id="totalp">720</h3>
     */
    public static final Target CART_TOTAL_PRICE =
        Target.the("precio total del carrito")
              .locatedBy("#totalp");

    /**
     * Botón "Place Order" que abre el modal de checkout.
     * HTML: <button class="btn btn-success" data-toggle="modal" data-target="#orderModal">Place Order</button>
     */
    public static final Target PLACE_ORDER_BUTTON =
        Target.the("botón 'Place Order' para proceder al pago")
              .locatedBy("//button[text()='Place Order']");

    /**
     * Botón para eliminar un producto del carrito.
     * Útil para pruebas de eliminación (escenarios futuros).
     */
    public static final Target DELETE_PRODUCT_LINK =
        Target.the("enlace para eliminar producto del carrito")
              .locatedBy("//td/a[text()='Delete']");
}
