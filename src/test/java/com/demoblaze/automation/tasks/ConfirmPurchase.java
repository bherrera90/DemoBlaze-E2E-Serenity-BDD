package com.demoblaze.automation.tasks;

import com.demoblaze.automation.interactions.ClickOn;
import com.demoblaze.automation.ui.targets.CheckoutModalTargets;
import com.demoblaze.automation.ui.targets.ConfirmationModalTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  ConfirmPurchase.java - Task: Confirmar la Compra                    ║
 * ║                                                                      ║
 * ║  ¿QUÉ HACE ESTA TASK?                                                ║
 * ║  Hace clic en el botón "Purchase" y espera que aparezca el           ║
 * ║  modal de confirmación con el mensaje de éxito.                      ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ ESTÁ SEPARADA DE CompleteCheckoutForm?                     ║
 * ║  Principio de RESPONSABILIDAD ÚNICA:                                 ║
 * ║  - CompleteCheckoutForm: responsable de LLENAR el formulario         ║
 * ║  - ConfirmPurchase: responsable de ENVIAR el formulario              ║
 * ║  Esto permite reutilizar CompleteCheckoutForm sin confirmar          ║
 * ║  (ej: test de validación de campos obligatorios).                    ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usa: ClickOn (Interaction)                                        ║
 * ║  - Usa: CheckoutModalTargets, ConfirmationModalTargets (Targets)     ║
 * ║  - Llamada desde: PurchaseFlowStepDefinitions                        ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("confirma la compra haciendo clic en 'Purchase'")
public class ConfirmPurchase implements Task {

    public ConfirmPurchase() {}

    /** actor.attemptsTo(ConfirmPurchase.order()); */
    public static ConfirmPurchase order() {
        return net.serenitybdd.screenplay.Tasks.instrumented(ConfirmPurchase.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {

        // ─────────────────────────────────────────────────────────────────
        //  PASO 1: Verificar que el botón Purchase está disponible
        //  (En caso de que esta Task se llame antes de CompleteCheckoutForm)
        // ─────────────────────────────────────────────────────────────────
        actor.attemptsTo(
            WaitUntil.the(CheckoutModalTargets.PURCHASE_BUTTON, isEnabled())
                     .forNoMoreThan(5).seconds()
        );

        // ─────────────────────────────────────────────────────────────────
        //  PASO 2: Hacer clic en "Purchase"
        //  Esto envía la petición al servidor y muestra el modal de éxito.
        // ─────────────────────────────────────────────────────────────────
        actor.attemptsTo(
            ClickOn.target(CheckoutModalTargets.PURCHASE_BUTTON)
        );

        // ─────────────────────────────────────────────────────────────────
        //  PASO 3: Esperar que aparezca el modal SweetAlert de confirmación
        //  La petición al servidor puede tardar 1-3 segundos.
        //  Esperamos hasta 15 segundos por si la red es lenta.
        // ─────────────────────────────────────────────────────────────────
        actor.attemptsTo(
            WaitUntil.the(ConfirmationModalTargets.CONFIRMATION_TITLE, isVisible())
                     .forNoMoreThan(15).seconds()
        );
    }
}
