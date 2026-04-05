package com.demoblaze.automation.tasks;

import com.demoblaze.automation.models.PurchaseFormData;
import com.demoblaze.automation.ui.targets.CheckoutModalTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.*;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  CompleteCheckoutForm.java - Task: Completar el Formulario de Compra ║
 * ║                                                                      ║
 * ║  ¿QUÉ HACE ESTA TASK?                                                ║
 * ║  1. Hace clic en el botón "Place Order"                              ║
 * ║  2. Espera que el modal de checkout aparezca y sea interactable       ║
 * ║  3. Llena todos los campos del formulario con los datos del comprador ║
 * ║                                                                      ║
 * ║  PARÁMETRO: PurchaseFormData (modelo con todos los datos del form)   ║
 * ║  Esto hace la Task REUTILIZABLE con diferentes conjuntos de datos.   ║
 * ║                                                                      ║
 * ║  SEPARACIÓN DE RESPONSABILIDADES:                                    ║
 * ║  Esta Task SOLO llena el formulario. NO hace clic en "Purchase".     ║
 * ║  La confirmación final es responsabilidad de ConfirmPurchase (Task). ║
 * ║  Esto permite probar el formulario sin confirmar la compra.          ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usa: Enter (Actions)                                              ║
 * ║  - Usa: CheckoutModalTargets (Targets)                               ║
 * ║  - Recibe: PurchaseFormData (Model)                                  ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("completa el formulario de checkout con los datos del comprador")
public class CompleteCheckoutForm implements Task {

    /** Los datos del formulario (nombre, país, ciudad, tarjeta, mes, año) */
    private final PurchaseFormData formData;

    // LOGGER: Trazabilidad empresarial para Debugging
    private static final Logger LOGGER = LoggerFactory.getLogger(CompleteCheckoutForm.class);

    private CompleteCheckoutForm(PurchaseFormData formData) {
        this.formData = formData;
    }

    /**
     * Método de fábrica.
     * USO: actor.attemptsTo(CompleteCheckoutForm.with(formData));
     */
    public static CompleteCheckoutForm with(PurchaseFormData formData) {
        return new CompleteCheckoutForm(formData);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        
        LOGGER.info("Iniciando completado de checkout (Purchase flow) para usuario: {}", formData.getName());

        try {
            // ─────────────────────────────────────────────────────────────────
            //  PASO 1: Esperar que el modal esté completamente visible.
            // ─────────────────────────────────────────────────────────────────
            actor.attemptsTo(
                WaitUntil.the(CheckoutModalTargets.ORDER_MODAL, isVisible())
                         .forNoMoreThan(10).seconds()
            );

            // ─────────────────────────────────────────────────────────────────
            //  PASO 2: Ingresar datos del formulario
            // ─────────────────────────────────────────────────────────────────
            LOGGER.debug("Llenando los campos del formulario de checkout");
            actor.attemptsTo(
                Enter.theValue(formData.getName()).into(CheckoutModalTargets.NAME_INPUT),
                Enter.theValue(formData.getCountry()).into(CheckoutModalTargets.COUNTRY_INPUT),
                Enter.theValue(formData.getCity()).into(CheckoutModalTargets.CITY_INPUT),
                Enter.theValue(formData.getCreditCard()).into(CheckoutModalTargets.CREDIT_CARD_INPUT),
                Enter.theValue(formData.getMonth()).into(CheckoutModalTargets.MONTH_INPUT),
                Enter.theValue(formData.getYear()).into(CheckoutModalTargets.YEAR_INPUT)
            );
            
            LOGGER.info("Se ha completado la inserción de datos en el Checkout Form");
        } catch (Exception e) {
            LOGGER.error("Fallo completando el formulario de checkout", e);
            throw e;
        }
    }
}
