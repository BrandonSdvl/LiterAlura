package com.alura.LiterAlura.model;

public enum Idioma {
    ES("es", "Español"),
    EN("en", "Inglés"),
    FR("fr", "Francés"),
    PT("pt", "Portugués");

    private String codigo;
    private String idioma;

    Idioma(String codigo, String idioma) {
        this.codigo = codigo;
        this.idioma = idioma;
    }

    public static Idioma desdeCodigo(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if(idioma.codigo.equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Idioma no registrado: " + codigo);
    }
}
