package com.demoblaze.automation.models;

/**
 * ╔══════════════════════════════════════════════════════════════════════╗
 * ║  PurchaseFormData.java - Modelo de Datos del Formulario de Compra   ║
 * ║                                                                      ║
 * ║  ¿QUÉ ES ESTE ARCHIVO?                                               ║
 * ║  Es un "Value Object" (Objeto de Valor) que representa los datos     ║
 * ║  que el usuario ingresa en el formulario de checkout.                ║
 * ║                                                                      ║
 * ║  ¿POR QUÉ EXISTE?                                                    ║
 * ║  - ENCAPSULACIÓN: Agrupa datos relacionados en un objeto coherente   ║
 * ║  - TIPO SEGURO: En lugar de pasar 6 Strings sueltos, pasas un objeto ║
 * ║  - LEGIBILIDAD: formData.getName() es más claro que args[0]          ║
 * ║  - MANTENIBILIDAD: Si cambia el formulario, solo cambias este modelo ║
 * ║                                                                      ║
 * ║  PATRÓN: Builder Pattern (via Lombok @Builder)                       ║
 * ║  Permite crear objetos así:                                          ║
 * ║  PurchaseFormData.builder().name("Juan").country("Colombia").build() ║
 * ║                                                                      ║
 * ║  RELACIONES:                                                         ║
 * ║  - Usado por: CompleteCheckoutForm (Task)                            ║
 * ║  - Creado en: PurchaseFlowStepDefinitions (Step Definitions)         ║
 * ╚══════════════════════════════════════════════════════════════════════╝
 */
public class PurchaseFormData {

    // ─────────────────────────────────────────────────────────────────────
    //  CAMPOS DEL FORMULARIO
    //  Corresponden exactamente a los campos del modal de checkout
    //  en https://www.demoblaze.com/
    // ─────────────────────────────────────────────────────────────────────

    /** Nombre completo del comprador. Campo "Name" en el formulario. */
    private final String name;

    /** País del comprador. Campo "Country" en el formulario. */
    private final String country;

    /** Ciudad del comprador. Campo "City" en el formulario. */
    private final String city;

    /** Número de tarjeta de crédito. Campo "Credit card" en el formulario. */
    private final String creditCard;

    /** Mes de expiración de la tarjeta. Campo "Month" en el formulario. */
    private final String month;

    /** Año de expiración de la tarjeta. Campo "Year" en el formulario. */
    private final String year;

    // ─────────────────────────────────────────────────────────────────────
    //  CONSTRUCTOR PRIVADO (Patrón Builder)
    //  Solo el Builder puede crear instancias, garantizando objetos válidos.
    // ─────────────────────────────────────────────────────────────────────
    private PurchaseFormData(Builder builder) {
        this.name = builder.name;
        this.country = builder.country;
        this.city = builder.city;
        this.creditCard = builder.creditCard;
        this.month = builder.month;
        this.year = builder.year;
    }

    // ─────────────────────────────────────────────────────────────────────
    //  GETTERS
    //  Acceso de solo lectura a los campos (objeto inmutable).
    //  La inmutabilidad evita errores de estado compartido en pruebas.
    // ─────────────────────────────────────────────────────────────────────
    public String getName()       { return name; }
    public String getCountry()    { return country; }
    public String getCity()       { return city; }
    public String getCreditCard() { return creditCard; }
    public String getMonth()      { return month; }
    public String getYear()       { return year; }

    // ─────────────────────────────────────────────────────────────────────
    //  STATIC FACTORY METHOD
    //  Punto de entrada para crear el Builder.
    //  Uso: PurchaseFormData.builder().name("Juan").build()
    // ─────────────────────────────────────────────────────────────────────
    public static Builder builder() {
        return new Builder();
    }

    // ─────────────────────────────────────────────────────────────────────
    //  BUILDER INNER CLASS
    //  Patrón de diseño Builder: permite construir objetos complejos
    //  paso a paso con un API fluido (method chaining).
    //
    //  VENTAJA sobre constructor con múltiples parámetros:
    //  new PurchaseFormData("Juan", "Colombia", "Bogota", "4111...", "12", "2025")
    //  vs.
    //  PurchaseFormData.builder().name("Juan").country("Colombia")...build()
    //  → La segunda versión es MUCHO más legible.
    // ─────────────────────────────────────────────────────────────────────
    public static class Builder {
        private String name;
        private String country;
        private String city;
        private String creditCard;
        private String month;
        private String year;

        // Cada método retorna 'this' para permitir encadenamiento de llamadas
        public Builder name(String name)             { this.name = name; return this; }
        public Builder country(String country)       { this.country = country; return this; }
        public Builder city(String city)             { this.city = city; return this; }
        public Builder creditCard(String creditCard) { this.creditCard = creditCard; return this; }
        public Builder month(String month)           { this.month = month; return this; }
        public Builder year(String year)             { this.year = year; return this; }

        /** Construye y retorna el objeto PurchaseFormData inmutable. */
        public PurchaseFormData build() {
            // Aquí podrías agregar validaciones:
            // if (name == null || name.isEmpty()) throw new IllegalStateException("Name is required");
            return new PurchaseFormData(this);
        }
    }

    @Override
    public String toString() {
        // Útil para logging; NO muestra la tarjeta completa (seguridad)
        return "PurchaseFormData{name='" + name + "', country='" + country +
               "', city='" + city + "', creditCard='****" +
               (creditCard != null ? creditCard.substring(Math.max(0, creditCard.length() - 4)) : "") +
               "', month='" + month + "', year='" + year + "'}";
    }
}
