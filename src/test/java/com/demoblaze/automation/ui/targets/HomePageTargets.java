package com.demoblaze.automation.ui.targets;

import net.serenitybdd.screenplay.targets.Target;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  HomePageTargets.java - Capa UI: Targets de la Página Principal      ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Define los SELECTORES (localizadores) de los elementos HTML         ║
 * ║  de la página principal de DemoBlaze.                                ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES UN TARGET EN SCREENPLAY?                                    ║
 * ║  En el patrón Screenplay, un Target es una abstracción sobre los     ║
 * ║  selectores de Selenium. En lugar de usar By.cssSelector() directa-  ║
 * ║  mente en el código, defines Targets con nombre descriptivo.         ║
 * ║                                                                      ║
 * ║  ANALOGÍA: Si Selenium WebDriver fuera un GPS, los Targets serían    ║
 * ║  los "puntos de interés" guardados en favoritos.                     ║
 * ║  GPS: "Ir a coordenadas 4.711, -74.072"  (difícil de leer)          ║
 * ║  Target: "Ir al BOTÓN AGREGAR AL CARRITO" (fácil de leer)           ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ NO USAR LOS SELECTORES DIRECTAMENTE EN EL CÓDIGO?         ║
 * ║  - Si el selector cambia, lo cambias en UN solo lugar (aquí)         ║
 * ║  - El código de Tasks queda limpio y legible                         ║
 * ║  - Facilita mantenimiento cuando la UI de la app cambia              ║
 * ║                                                                      ║
 * ║  CONVENCIÓN: Las constantes se definen en UPPER_SNAKE_CASE           ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: AddProductToCart (Task), NavigateTo (Interaction)      ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
public class HomePageTargets {

    /*
     * ──────────────────────────────────────────────────────────────────────
     *  NAVEGACIÓN PRINCIPAL
     *  El menú de navegación de DemoBlaze está en la barra superior (navbar).
     * ──────────────────────────────────────────────────────────────────────
     */

    /**
     * El enlace al carrito en la barra de navegación.
     *
     * Target.the("...") → Nombre descriptivo que aparecerá en el reporte Serenity
     * locatedBy("...") → El selector CSS para encontrar el elemento en el HTML
     *
     * ¿Cómo encontrar el selector CSS?
     * 1. Abre Chrome DevTools (F12)
     * 2. Haz clic en el elemento
     * 3. Ve a la pestaña "Elements"
     * 4. Clic derecho → Copy → Copy selector
     *
     * HTML de DemoBlaze: <a id="cartur" href="/cart.html">Cart</a>
     */
    public static final Target CART_LINK =
        Target.the("enlace al carrito en la navegación")
              .locatedBy("#cartur");

    /**
     * Logo de DemoBlaze que también sirve como enlace a la página principal.
     * Útil para navegar al inicio desde cualquier página.
     */
    public static final Target HOME_LOGO =
        Target.the("logo de DemoBlaze (enlace al inicio)")
              .locatedBy(".navbar-brand");

    /*
     * ──────────────────────────────────────────────────────────────────────
     *  CATEGORÍAS DE PRODUCTOS
     *  DemoBlaze tiene categorías: Phones, Laptops, Monitors
     * ──────────────────────────────────────────────────────────────────────
     */

    /**
     * Lista de categorías en el sidebar izquierdo.
     * #itemc → ID del contenedor de categorías
     *
     * NOTA: No necesitamos hacer clic en categorías para este flujo,
     * pero los incluimos para futuros escenarios (preparado para escalar).
     */
    public static final Target PHONES_CATEGORY =
        Target.the("categoría Phones")
              .locatedBy("a[onclick=\"byCat('phone')\"]");

    public static final Target LAPTOPS_CATEGORY =
        Target.the("categoría Laptops")
              .locatedBy("a[onclick=\"byCat('notebook')\"]");

    /*
     * ──────────────────────────────────────────────────────────────────────
     *  LISTA DE PRODUCTOS
     *  Los productos se muestran como tarjetas (cards) en la página.
     *  DemoBlaze carga los productos mediante JavaScript (AJAX),
     *  por eso necesitamos esperas explícitas.
     * ──────────────────────────────────────────────────────────────────────
     */

    /**
     * Localiza un producto específico POR NOMBRE en la página.
     *
     * {0} es un PLACEHOLDER que se reemplaza dinámicamente.
     * Ejemplo: locatedBy("//a[normalize-space(text())='{0}']", "Samsung galaxy s6")
     * → locatedBy("//a[normalize-space(text())='Samsung galaxy s6']")
     *
     * XPath explanation:
     * //a → cualquier elemento <a> (enlace) en cualquier parte del DOM
     * [normalize-space(text())='Samsung galaxy s6'] → cuyo texto (sin espacios extra) sea ese
     *
     * ¿Por qué XPath aquí?
     * CSS no tiene selector "por texto". XPath sí.
     * Esto hace el selector DINÁMICO: un solo Target para todos los productos.
     */
    public static final Target PRODUCT_LINK_BY_NAME =
        Target.the("producto '{0}' en el catálogo")
              .locatedBy("//a[normalize-space(text())='{0}']");

    /**
     * Contenedor principal de la lista de productos.
     * Usado para verificar que los productos han cargado antes de interactuar.
     */
    public static final Target PRODUCTS_CONTAINER =
        Target.the("contenedor de la lista de productos")
              .locatedBy("#tbodyid");
}
