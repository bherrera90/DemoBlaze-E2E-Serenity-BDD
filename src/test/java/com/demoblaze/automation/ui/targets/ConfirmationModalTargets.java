package com.demoblaze.automation.ui.targets;

import net.serenitybdd.screenplay.targets.Target;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  ConfirmationModalTargets.java - Targets del Modal de Confirmación  ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Define los selectores del modal que aparece DESPUÉS de confirmar    ║
 * ║  la compra. Este modal muestra el resumen de la orden.               ║
 * ║                                                                      ║
 * ║  DATOS QUE MUESTRA EL MODAL DE CONFIRMACIÓN:                         ║
 * ║  - Mensaje: "Thank you for your purchase!"                           ║
 * ║  - ID de la orden: "Id: 8647855"                                     ║
 * ║  - Monto: "Amount: 720 USD"                                          ║
 * ║  - Fecha de entrega estimada                                         ║
 * ║  - Información de la tarjeta: "Card Number: 4111111111111"           ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: ConfirmationMessage (Question)                         ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
public class ConfirmationModalTargets {

    /**
     * Contenedor del modal de confirmación de compra.
     * HTML: <div class="sweet-alert showSweetAlert visible" ...>
     *
     * DemoBlaze usa SweetAlert (librería de popups) para la confirmación.
     * No es un modal Bootstrap estándar, es SweetAlert.
     *
     * NOTA TÉCNICA: .sweet-alert es la clase de SweetAlert 1.x
     */
    public static final Target CONFIRMATION_MODAL =
        Target.the("modal de confirmación de compra exitosa (SweetAlert)")
              .locatedBy(".sweet-alert");

    /**
     * Mensaje principal de confirmación.
     * HTML: <h2>Thank you for your purchase!</h2>
     *
     * Este es el elemento CRÍTICO para la validación final del flujo.
     * Si este texto aparece, la compra fue exitosa.
     */
    public static final Target CONFIRMATION_TITLE =
        Target.the("título del mensaje de confirmación 'Thank you for your purchase!'")
              .locatedBy(".sweet-alert h2");

    /**
     * Párrafo con el detalle completo de la orden.
     * HTML: <p class="lead text-muted">Id: 8647855\nAmount: 720 USD\n...</p>
     *
     * Este texto contiene múltiples líneas con:
     * - Id de la orden
     * - Monto total
     * - Fecha de entrega
     * - Número de tarjeta
     */
    public static final Target CONFIRMATION_DETAILS =
        Target.the("párrafo con detalles de la orden confirmada")
              .locatedBy(".sweet-alert p.lead");

    /**
     * Botón "OK" para cerrar el modal de confirmación.
     * HTML: <button class="confirm">OK</button>
     */
    public static final Target CONFIRMATION_OK_BUTTON =
        Target.the("botón 'OK' para cerrar la confirmación de compra")
              .locatedBy(".sweet-alert .confirm");
}
