package com.alura.LiterAlura.repository;

import com.alura.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
}