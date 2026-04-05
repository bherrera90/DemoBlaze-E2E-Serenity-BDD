# language: es
# ╔══════════════════════════════════════════════════════════════════════╗
# ║  flujo_de_compra.feature - El archivo Gherkin de la prueba           ║
# ║                                                                      ║
# ║  ¿QUÉ ES ESTE ARCHIVO? (Formato BDD / Gherkin)                       ║
# ║                                                                      ║
# ║  Es donde se DESCRIBE el comportamiento esperado del sistema         ║
# ║  usando lenguaje natural estructurado (Gherkin).                     ║
# ║                                                                      ║
# ║  ANALOGÍA: Es el "contrato" entre el negocio y el equipo técnico.    ║
# ║  Un Product Owner no técnico puede leer y entender qué se automatiza.║
# ║                                                                      ║
# ║  ESTRUCTURA GHERKIN:                                                 ║
# ║  Feature:   Describe la funcionalidad a probar (1 por archivo)       ║
# ║  Scenario:  Un caso de prueba específico                             ║
# ║  Given:     Contexto inicial / Precondiciones                        ║
# ║  When:      Acción que realiza el usuario                            ║
# ║  Then:      Resultado esperado / Validación                          ║
# ║  And/But:   Continúa el Given/When/Then anterior                     ║
# ║                                                                      ║
# ║  ¿POR QUÉ EXISTE SEPARADO DEL CÓDIGO JAVA?                           ║
# ║  - Separación de responsabilidades: QUÉ vs CÓMO                      ║
# ║  - El .feature define QUÉ se prueba (negocio)                        ║
# ║  - El código Java define CÓMO se ejecuta (técnico)                   ║
# ║                                                                      ║
# ║  UBICACIÓN: src/test/resources/features/flujo_de_compra.feature      ║
# ╚══════════════════════════════════════════════════════════════════════╝

@FlujoDeCompra @E2E
Característica: Flujo completo de compra en DemoBlaze
  Como cliente de DemoBlaze
  Quiero agregar productos al carrito y completar una compra
  Para recibir los productos que necesito

  Antecedentes:
    # El Antecedente (Background) se ejecuta ANTES de cada Escenario.
    # Es el punto de partida común para todos los casos de prueba.
    # Aquí preparamos al "Actor" (el usuario virtual de Screenplay).
    Dado que Jorge es un cliente que navega en DemoBlaze

  @CompraExitosa @MochilaYSamsung
  Escenario: Compra exitosa de dos productos con todos los datos válidos
    # ─────────────────────────────────────────────────────────────
    #  FASE 1: AGREGAR PRODUCTOS AL CARRITO
    #  El cliente navega al catálogo y agrega dos productos
    # ─────────────────────────────────────────────────────────────
    Cuando Jorge agrega el producto "Samsung galaxy s6" al carrito
    Y Jorge agrega el producto "Nokia lumia 1520" al carrito

    # ─────────────────────────────────────────────────────────────
    #  FASE 2: VISUALIZAR EL CARRITO
    #  El cliente verifica que sus productos están en el carrito
    # ─────────────────────────────────────────────────────────────
    Cuando Jorge navega al carrito de compras
    Entonces el carrito debería contener los productos seleccionados

    # ─────────────────────────────────────────────────────────────
    #  FASE 3: COMPLETAR EL FORMULARIO DE COMPRA
    #  El cliente llena el formulario con sus datos personales
    # ─────────────────────────────────────────────────────────────
    Cuando Jorge inicia el proceso de compra
    Y Jorge completa el formulario con los siguientes datos:
      | nombre  | pais     | ciudad   | tarjeta       | mes | año  |
      | Juan QA | Colombia | Bogota   | 4111111111111 | 12  | 2025 |

    # ─────────────────────────────────────────────────────────────
    #  FASE 4: CONFIRMACIÓN DE COMPRA
    #  Validar que la compra se completó exitosamente
    # ─────────────────────────────────────────────────────────────
    Cuando Jorge confirma la compra
    Entonces debería ver el mensaje de confirmación "Thank you for your purchase!"
    Y el ID de la orden debería estar presente en la confirmación
