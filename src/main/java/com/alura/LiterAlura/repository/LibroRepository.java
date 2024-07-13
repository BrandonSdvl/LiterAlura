package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {
    @Query("SELECT DISTINCT LOWER(l.idioma) FROM Libro l")
    List<String> listaDeIdiomas();

    List<Libro> findByIdiomaIgnoreCase(String idioma);
}
