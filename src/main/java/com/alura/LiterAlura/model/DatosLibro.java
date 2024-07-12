package com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") int noDescargas
) {

    @Override
    public String toString() {
        String autoresString = autores.size() == 1 ? "Autor: " : "Autores: ";
        autoresString += autores.stream()
                .map(DatosAutor::nombre)
                .reduce((a, b) -> a + ", " + b)
                .orElse("N/A");

        String idiomasString = idiomas.size() == 1 ? "Idioma: " : "Idiomas: ";
        idiomasString += String.join(", ", idiomas);

        return String.format("""
                -------- LIBRO --------
                Título: %s
                %s
                %s
                Número de descargas: %d
                -----------------------
                """, titulo, autoresString, idiomasString, noDescargas);
    }
}
