package com.demoblaze.automation.stepdefinitions;

import com.demoblaze.automation.models.PurchaseFormData;
import com.demoblaze.automation.questions.CartContents;
import com.demoblaze.automation.questions.ConfirmationMessage;
import com.demoblaze.automation.tasks.*;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.es.*;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.GivenWhenThen;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  PurchaseFlowStepDefinitions.java - El Puente entre Gherkin y Java   ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Es el "traductor" entre el lenguaje de negocio (Gherkin) y el       ║
 * ║  código Java. Cada método anotado con @Dado, @Cuando, @Entonces       ║
 * ║  corresponde a un paso del archivo .feature.                         ║
 * ║                                                                      ║
 * ║  MAPEO GHERKIN → JAVA:                                               ║
 * ║  Gherkin: "Dado que Jorge es un cliente..."                          ║
 * ║  Java:    @Dado("que {word} es un cliente que navega en DemoBlaze") ║
 * ║           public void setupActor(String actorName) { ... }           ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ SEPARAR STEP DEFS DEL CÓDIGO DE AUTOMATIZACIÓN?           ║
 * ║  - Los Step Defs son DELGADOS: solo orquestan Tasks                  ║
 * ║  - La lógica real está en Tasks/Interactions/Questions               ║
 * ║  - Cambiar la UI solo requiere cambiar Targets, no Step Defs         ║
 * ║                                                                      ║
 * ║  PATRÓN SCREENPLAY EN PASO:                                          ║
 * ║  1. Obtener el actor: OnStage.theActorCalled("Jorge")                ║
 * ║  2. El actor intenta: actor.attemptsTo(Task)                         ║
 * ║  3. El actor valida: actor.should(seeThat(Question, matcher))        ║
 * ║                                                                      ║
 * ║  ANOTACIONES CUCUMBER EN ESPAÑOL:                                    ║
 * ║  @Dado = @Given (contexto inicial)                                   ║
 * ║  @Cuando = @When (acción del usuario)                                ║
 * ║  @Entonces = @Then (validación del resultado)                        ║
 * ║  @Y = @And (continuación del paso anterior)                          ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
public class PurchaseFlowStepDefinitions {

    private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseFlowStepDefinitions.class);

    // ─────────────────────────────────────────────────────────────────────
    //  @Before - Se ejecuta ANTES de cada Escenario
    //  Inicializa el "escenario" Screenplay (Stage = "escenario de teatro")
    //  OnlineCast = el conjunto de actores disponibles con capacidades web
    //
    //  Analogía: Antes de empezar una obra de teatro, se prepara el escenario.
    // ─────────────────────────────────────────────────────────────────────
    @Before
    public void setTheStage() {
        LOGGER.info("====== INICIANDO NUEVO ESCENARIO E2E ======");
        // OnlineCast.sittingAtTheirComputers() → crea actores que pueden usar navegadores
        // Serenity configura automáticamente ChromeDriver basado en serenity.conf
        OnStage.setTheStage(new OnlineCast());
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Antecedente - Configurar el Actor
    //
    //  Gherkin: "Dado que Jorge es un cliente que navega en DemoBlaze"
    //  {word} → captura una palabra (el nombre del actor: "Jorge")
    //
    //  ¿Por qué usar un actor con nombre?
    //  - Múltiples actores en un escenario (ej: cliente + admin)
    //  - El nombre aparece en los reportes de Serenity
    //  - Más legible que usar "el actor"
    // ─────────────────────────────────────────────────────────────────────
    @Dado("que {word} es un cliente que navega en DemoBlaze")
    public void setupActor(String actorName) {
        LOGGER.info("Configurando el actor principal: {}", actorName);
        // OnStage.theActorCalled() crea el actor si no existe, o lo recupera
        Actor actor = OnStage.theActorCalled(actorName);

        // El actor navega a la página principal como primera acción
        actor.attemptsTo(
            com.demoblaze.automation.interactions.NavigateTo.theHomePage()
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Agregar un producto al carrito
    //
    //  Gherkin: "Cuando Jorge agrega el producto "Samsung galaxy s6" al carrito"
    //  {word} → nombre del actor ("Jorge")
    //  {string} → valor entre comillas ("Samsung galaxy s6")
    // ─────────────────────────────────────────────────────────────────────
    @Cuando("{word} agrega el producto {string} al carrito")
    public void addProductToCart(String actorName, String productName) {
        LOGGER.info("Actor {} inicia proceso de agregar producto: {}", actorName, productName);
        // theActorInTheSpotlight() → el actor activo actual en el escenario
        // theActorCalled(name) → actor específico por nombre
        OnStage.theActorCalled(actorName).attemptsTo(
            AddProductToCart.named(productName)
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Navegar al carrito
    // ─────────────────────────────────────────────────────────────────────
    @Cuando("{word} navega al carrito de compras")
    public void navigateToCart(String actorName) {
        LOGGER.info("Actor {} navegando hacia la página del carrito", actorName);
        OnStage.theActorCalled(actorName).attemptsTo(
            ViewCart.products()
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Validar que el carrito tiene productos
    //
    //  PATRÓN SCREENPLAY PARA VALIDACIONES:
    //  actor.should(seeThat(Question, matcher))
    //  → "El actor debería ver que (la pregunta) (cumple la condición)"
    //
    //  seeThat() → método estático de GivenWhenThen
    //  is(true) → matcher de Hamcrest: "es verdadero"
    // ─────────────────────────────────────────────────────────────────────
    @Entonces("el carrito debería contener los productos seleccionados")
    public void verifyCartHasProducts() {
        LOGGER.info("Validando aserción visual: El carrito contiene los productos seleccionados");
        OnStage.theActorInTheSpotlight().should(
            seeThat(CartContents.hasProducts(), is(true))
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Iniciar el proceso de compra (abrir modal Place Order)
    // ─────────────────────────────────────────────────────────────────────
    @Cuando("{word} inicia el proceso de compra")
    public void initiateCheckout(String actorName) {
        LOGGER.info("Actor {} iniciando proceso de checkout clickeando Place Order", actorName);
        net.serenitybdd.screenplay.actors.OnStage.theActorCalled(actorName).attemptsTo(
            com.demoblaze.automation.interactions.ClickOn.target(com.demoblaze.automation.ui.targets.CartPageTargets.PLACE_ORDER_BUTTON)
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Completar formulario con DataTable
    //
    //  DataTable es la tabla de Gherkin:
    //  | nombre  | pais     | ciudad   | tarjeta       | mes | año  |
    //  | Juan QA | Colombia | Bogota   | 4111111111111 | 12  | 2025 |
    //
    //  DataTable.asMaps() → Lista de Map<String, String>
    //  Cada Map es una fila: {"nombre": "Juan QA", "pais": "Colombia", ...}
    // ─────────────────────────────────────────────────────────────────────
    @Y("{word} completa el formulario con los siguientes datos:")
    public void completeCheckoutForm(String actorName, DataTable dataTable) {
        LOGGER.info("Actor {} completando formulario de pago con datos del DataTable", actorName);
        // Convertir la primera (y única) fila de la tabla en un Map
        Map<String, String> formRow = dataTable.asMaps().get(0);

        // Construir el modelo con el patrón Builder
        PurchaseFormData formData = PurchaseFormData.builder()
            .name(formRow.get("nombre"))
            .country(formRow.get("pais"))
            .city(formRow.get("ciudad"))
            .creditCard(formRow.get("tarjeta"))
            .month(formRow.get("mes"))
            .year(formRow.get("año"))
            .build();

        // El actor intenta completar el formulario
        OnStage.theActorCalled(actorName).attemptsTo(
            CompleteCheckoutForm.with(formData)
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Confirmar la compra
    // ─────────────────────────────────────────────────────────────────────
    @Cuando("{word} confirma la compra")
    public void confirmPurchase(String actorName) {
        LOGGER.info("Actor {} confirmando la compra final", actorName);
        OnStage.theActorCalled(actorName).attemptsTo(
            ConfirmPurchase.order()
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Validar mensaje de confirmación
    //
    //  Gherkin: "Entonces debería ver el mensaje de confirmación "Thank you...""
    //  {string} → captura el texto esperado del mensaje de confirmación
    // ─────────────────────────────────────────────────────────────────────
    @Entonces("debería ver el mensaje de confirmación {string}")
    public void verifySuccessMessage(String expectedMessage) {
        LOGGER.info("Validando aserción visual: Mensaje de éxito visible: {}", expectedMessage);
        
        OnStage.theActorInTheSpotlight().should(
            seeThat(
                ConfirmationMessage.title(),
                containsString(expectedMessage)
            )
        );
    }

    // ─────────────────────────────────────────────────────────────────────
    //  PASO: Validar que la orden tiene un ID
    //
    //  Los detalles incluyen: "Id: 8647855\nAmount: 720 USD\n..."
    //  Verificamos que contenga "Id:" para confirmar que se generó una orden.
    // ─────────────────────────────────────────────────────────────────────
    @Y("el ID de la orden debería estar presente en la confirmación")
    public void verifyPurchaseData() {
        LOGGER.info("Validando aserción visual: Datos de la compra generados correctamente (Id, Amount)");
        OnStage.theActorInTheSpotlight().should(
            seeThat(
                ConfirmationMessage.details(),
                containsString("Id:")
            )
        );
    }
}
