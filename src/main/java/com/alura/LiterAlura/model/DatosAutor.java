package com.alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaFallecimiento
) {

    @Override
    public String toString() {
        return String.format("""
                Autor: %s
                Fecha de nacimiento: %s
                Fecha de fallecimiento: %s""", nombre, fechaNacimiento, fechaFallecimiento);
    }
}