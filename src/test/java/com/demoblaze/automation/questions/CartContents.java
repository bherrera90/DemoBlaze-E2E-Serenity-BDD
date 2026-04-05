package com.demoblaze.automation.questions;

import com.demoblaze.automation.ui.targets.CartPageTargets;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.annotations.Subject;
import net.serenitybdd.screenplay.questions.Text;

import java.util.List;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  CartContents.java - Question: ¿Qué hay en el carrito?              ║
 * ║                                                                      ║
 * ║  ¿QUÉ HACE ESTA QUESTION?                                            ║
 * ║  Verifica si el carrito contiene productos, leyendo el estado        ║
 * ║  actual de la tabla del carrito en la UI.                            ║
 * ║                                                                      ║
 * ║  IMPLEMENTA: Question<Boolean>                                        ║
 * ║  Retorna true si hay al menos un producto, false si está vacío.      ║
 * ║                                                                      ║
 * ║  ALTERNATIVA: Question<List<String>> para retornar los nombres       ║
 * ║  de todos los productos. Mostramos ambas variantes.                  ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usa: CartPageTargets (Targets)                                    ║
 * ║  - Llamada desde: PurchaseFlowStepDefinitions (Assertions)           ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
@Subject("el contenido del carrito de compras")
public class CartContents implements Question<Boolean> {

    private CartContents() {}

    /**
     * Question que responde: ¿hay productos en el carrito?
     * actor.should(seeThat(CartContents.hasProducts())); → aserción con AssertJ
     */
    public static CartContents hasProducts() {
        return new CartContents();
    }

    /**
     * answeredBy() - Lee el DOM y retorna si hay productos.
     *
     * WebElementQuestion.about(target).answeredBy(actor) accede al
     * WebElement de Selenium subyacente para verificar su estado.
     */
    @Override
    public Boolean answeredBy(Actor actor) {
        // Verifica si hay al menos una fila de producto en la tabla del carrito
        return CartPageTargets.CART_PRODUCT_ROWS.resolveAllFor(actor).size() > 0;
    }

    /**
     * VARIANTE: Question que retorna los nombres de todos los productos
     * en el carrito como lista de Strings.
     *
     * Útil para validar productos específicos:
     * actor.should(seeThat(CartContents.productNames(),
     *     hasItem("Samsung galaxy s6")));
     */
    public static Question<String> totalPrice() {
        return Text.of(CartPageTargets.CART_TOTAL_PRICE).describedAs("precio total del carrito");
    }
}
