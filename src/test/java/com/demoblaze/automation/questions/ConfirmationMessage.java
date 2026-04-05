package com.demoblaze.automation.questions;

import com.demoblaze.automation.ui.targets.ConfirmationModalTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.questions.Text;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  ConfirmationMessage.java - Question: ¿Qué dice el mensaje de       ║
 * ║                              confirmación?                           ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES UNA QUESTION EN SCREENPLAY?                                 ║
 * ║                                                                      ║
 * ║  Las Questions extraen información del sistema para ser validada.    ║
 * ║  Son el equivalente al "Then" de Gherkin (la validación).           ║
 * ║                                                                      ║
 * ║  JERARQUÍA SCREENPLAY COMPLETA:                                      ║
 * ║  Actor → should → Question (extrae info) → que se valida con assertThat║
 * ║                                                                      ║
 * ║  DIFERENCIA con Tasks:                                               ║
 * ║  - Task: HACE algo en el sistema (acción con efecto secundario)      ║
 * ║  - Question: OBSERVA el sistema (sin efecto secundario, solo lectura)║
 * ║                                                                      ║
 * ║  ANALOGÍA:                                                           ║
 * ║  Task = Un detective que interroga a un testigo (acción)             ║
 * ║  Question = La libreta donde anota lo que observa (lectura)          ║
 * ║                                                                      ║
 * ║  USO EN STEP DEFINITIONS:                                            ║
 * ║  actor.should(seeThat(ConfirmationMessage.title(),                   ║
 * ║       containsString("Thank you")));                                 ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usa: ConfirmationModalTargets (Targets)                           ║
 * ║  - Usa: Text.of() (Question incorporada de Serenity)                 ║
 * ║  - Llamada desde: PurchaseFlowStepDefinitions (Assertions)           ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("el mensaje de confirmación de compra")
public class ConfirmationMessage implements Question<String> {

    private final ConfirmationPart part;

    // Enum para indicar QUÉ parte del modal queremos leer
    public enum ConfirmationPart { TITLE, DETAILS }

    private ConfirmationMessage(ConfirmationPart part) {
        this.part = part;
    }

    /**
     * Retorna la Question que lee el TÍTULO del modal de confirmación.
     * ("Thank you for your purchase!")
     *
     * IMPLEMENTA Question<String> → answeredBy() retorna un String
     */
    public static ConfirmationMessage title() {
        return new ConfirmationMessage(ConfirmationPart.TITLE);
    }

    /**
     * Retorna la Question que lee los DETALLES de la orden.
     * (ID de orden, monto, fecha, tarjeta)
     */
    public static ConfirmationMessage details() {
        return new ConfirmationMessage(ConfirmationPart.DETAILS);
    }

    /**
     * answeredBy() - El método core de toda Question.
     *
     * Este método es llamado por Serenity cuando el actor "pregunta" algo.
     * Retorna el valor observado del sistema.
     *
     * Text.of(target) → Interaction de Serenity que extrae el texto de un elemento
     * .answeredBy(actor) → Lo "ejecuta" en el contexto del actor
     */
    @Override
    public String answeredBy(Actor actor) {
        switch (part) {
            case TITLE:
                // Obtiene el texto del <h2> de SweetAlert
                return Text.of(ConfirmationModalTargets.CONFIRMATION_TITLE)
                           .answeredBy(actor);
            case DETAILS:
                // Obtiene el texto del <p> con los detalles de la orden
                return Text.of(ConfirmationModalTargets.CONFIRMATION_DETAILS)
                           .answeredBy(actor);
            default:
                throw new IllegalStateException("ConfirmationPart desconocido: " + part);
        }
    }
}
