package com.demoblaze.automation.ui.targets;

import net.serenitybdd.screenplay.targets.Target;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  ProductPageTargets.java - Targets de la Página de Producto         ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Define los selectores de los elementos en la página de detalle      ║
 * ║  de un producto (la que ves cuando haces clic en un producto).       ║
 * ║                                                                      ║
 * ║  URL ejemplo: https://www.demoblaze.com/prod.html?idp_=1             ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ UN ARCHIVO SEPARADO DE HomePageTargets?                    ║
 * ║  PRINCIPIO: Single Responsibility (una clase, una responsabilidad)   ║
 * ║  - HomePageTargets → solo página principal                           ║
 * ║  - ProductPageTargets → solo página de producto                      ║
 * ║  Si mañana cambia solo la página de producto, solo editas este archivo║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: AddProductToCart (Task)                                ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
public class ProductPageTargets {

    /**
     * Botón "Add to cart" en la página de detalle del producto.
     *
     * HTML de DemoBlaze:
     * <a onclick="addToCart(1)" class="btn btn-success btn-lg">Add to cart</a>
     *
     * Selector CSS breakdown:
     * .btn-success → clase CSS del botón verde de Bootstrap
     *
     * ALTERNATIVA más robusta con XPath:
     * "//a[contains(text(), 'Add to cart')]"
     * Preferimos CSS por ser más rápido que XPath.
     */
    public static final Target ADD_TO_CART_BUTTON =
        Target.the("botón 'Add to cart' en la página del producto")
              .locatedBy(".btn-success");

    /**
     * Nombre del producto en la página de detalle.
     * Usado para verificar que llegamos al producto correcto.
     *
     * HTML: <h2 class="name">Samsung galaxy s6</h2>
     */
    public static final Target PRODUCT_NAME_TITLE =
        Target.the("título/nombre del producto en la página de detalle")
              .locatedBy(".name");

    /**
     * Precio del producto en la página de detalle.
     * HTML: <h3 class="price-container">$360 *includes tax</h3>
     */
    public static final Target PRODUCT_PRICE =
        Target.the("precio del producto")
              .locatedBy(".price-container");

    /**
     * Descripción del producto.
     * HTML: <p id="more-information">...</p>
     */
    public static final Target PRODUCT_DESCRIPTION =
        Target.the("descripción del producto")
              .locatedBy("#more-information");
}
