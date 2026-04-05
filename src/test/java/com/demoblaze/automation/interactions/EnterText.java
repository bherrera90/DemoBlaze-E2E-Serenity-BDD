package com.demoblaze.automation.interactions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Clear;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.targets.Target;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  EnterText.java - Interaction: Ingresar texto en un campo           ║
 * ║                                                                      ║
 * ║  ¿QUÉ HACE ESTA INTERACTION?                                         ║
 * ║  Limpia un campo de texto y luego escribe el valor deseado.          ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ LIMPIAR PRIMERO?                                           ║
 * ║  Si el campo ya tiene texto (de ejecuciones anteriores o de datos    ║
 * ║  por defecto), hacer Enter.theValue() SIN limpiar CONCATENARÍA       ║
 * ║  el nuevo texto al existente.                                         ║
 * ║  Ejemplo sin limpieza: "placeholder_text" + "Juan" = mala entrada   ║
 * ║                                                                      ║
 * ║  ANALOGÍA: Como borrar un pizarrón antes de escribir.                ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: CompleteCheckoutForm (Task)                            ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("ingresa '#value' en '#target'")
public class EnterText implements Interaction {

    private final Target target;
    private final String  value;

    private EnterText(Target target, String value) {
        this.target = target;
        this.value  = value;
    }

    /**
     * API fluido: EnterText.of("Juan").into(CheckoutModalTargets.NAME_INPUT)
     * Permite leer el código como si fuera inglés natural.
     */
    public static EnterTextBuilder of(String value) {
        return new EnterTextBuilder(value);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            // Paso 1: Limpiar el campo (borra cualquier texto previo)
            Clear.field(target),
            // Paso 2: Escribir el nuevo valor
            // Enter.theValue() es la forma Screenplay de sendKeys()
            Enter.theValue(value).into(target)
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  BUILDER INNER CLASS para API fluido: EnterText.of("valor").into(target)
    // ─────────────────────────────────────────────────────────────────────
    public static class EnterTextBuilder {
        private final String value;

        private EnterTextBuilder(String value) {
            this.value = value;
        }

        public EnterText into(Target target) {
            return new EnterText(target, value);
        }
    }
}
