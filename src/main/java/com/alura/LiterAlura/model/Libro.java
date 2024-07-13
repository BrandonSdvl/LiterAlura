package com.alura.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.ALL)
    private Autor autor;
    private String idioma;
    private int noDescargas;

    public Libro(){}

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.autor = new Autor(datosLibro.autores().get(0));
        this.idioma = datosLibro.idiomas().get(0);
        this.noDescargas = datosLibro.noDescargas();
    }

    @Override
    public String toString() {
        return String.format("""
                -------- LIBRO --------
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d
                -----------------------
                """, titulo, autor.getNombre(), idioma, noDescargas);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public int getNoDescargas() {
        return noDescargas;
    }

    public void setNoDescargas(int noDescargas) {
        this.noDescargas = noDescargas;
    }
}
