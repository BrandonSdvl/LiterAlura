package com.alura.LiterAlura.service;

import com.alura.LiterAlura.Principal.EntradaUsuario;
import com.alura.LiterAlura.model.Autor;
import com.alura.LiterAlura.model.DatosAutor;
import com.alura.LiterAlura.model.DatosLibro;
import com.alura.LiterAlura.model.Libro;
import com.alura.LiterAlura.repository.AutorRepository;
import com.alura.LiterAlura.repository.LibroRepository;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Buscador {
    private final ConsumoAPI consumoApi = new ConsumoAPI();
    private final Scanner teclado = new Scanner(System.in);
    private final ConvierteDatos conversor = new ConvierteDatos();
    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final EntradaUsuario entradaUsuario;

    public Buscador(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.entradaUsuario = new EntradaUsuario(teclado);
    }

    public void buscarPorTitulo() {
        String busqueda = entradaUsuario.obtenerEntradaUsuario("Ingrese el nombre del libro que desea buscar: ");
        String URL_BASE = "https://gutendex.com";
        String json = consumoApi.obtenerDatos(URL_BASE + "/books/?search=" + busqueda.replace(" ", "%20"));

        try {
            JsonNode rootNode = conversor.obtenerDatos(json, JsonNode.class);
            JsonNode resultsNode = rootNode.get("results");

            if(resultsNode == null || resultsNode.isEmpty()) {
                System.out.println("No se encontraron resultados para la búsqueda. Intente con otro título.");
                return;
            }

            DatosLibro datosLibro = conversor.obtenerDatos(resultsNode.get(0).toString(), DatosLibro.class);
            DatosAutor datosAutor = obtenerDatosAutor(datosLibro);

            Libro tmpLibro = libroRepository.findByTitulo(datosLibro.titulo());

            if(tmpLibro != null){
                System.out.println("Este libro ya ha sido registrado...\n");
                System.out.println(tmpLibro);
            } else {
                Libro libro = new Libro(datosLibro, obtenerORegistrarAutor(datosAutor));
                libroRepository.save(libro);
                System.out.println(libro);
            }
        } catch (Exception e) {
            System.err.println("Error al buscar libro: " + e.getMessage());
        }
    }

    public DatosAutor obtenerDatosAutor(DatosLibro datosLibro) {
        return Optional.ofNullable(datosLibro.autores())
                .filter(autores -> !autores.isEmpty())
                .map(autores -> autores.get(0))
                .orElse(new DatosAutor("Desconocido", null, null));
    }

    public Autor obtenerORegistrarAutor(DatosAutor datosAutor) {
        return Optional.ofNullable(autorRepository.findByNombre(datosAutor.nombre()))
                .orElseGet(() -> {
                    Autor autor = new Autor(datosAutor);
                    autorRepository.save(autor);
                    return autor;
                });
    }

    public void autoresPorAnio() {
        int anio = entradaUsuario.obtenerEntradaNumerica("Ingrese el año a buscar: ");
        List<Autor> autores = autorRepository.autoresPorFecha(anio);

        if (autores.isEmpty()) {
            System.out.println("No se ha encontrado ningún autor...\n");
        } else {
            autores.forEach(System.out::println);
        }
    }

    public void librosPorIdioma() {
        List<String> idiomas = libroRepository.listaDeIdiomas();

        if(idiomas.isEmpty()) {
            System.out.println("Aun no hay libros registrados...");
            return;
        }

        String idiomaSeleccionado;
        do {
            System.out.println("Idiomas disponibles:");
            idiomas.forEach(System.out::println);
            idiomaSeleccionado = entradaUsuario.obtenerEntradaUsuario("Seleccione uno de los idiomas registrados: ").toLowerCase();
        } while(!idiomas.contains(idiomaSeleccionado));

        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idiomaSeleccionado);
        System.out.println("Libros en idioma '" + idiomaSeleccionado + "':");
        libros.forEach(System.out::println);
    }

    public void listarLibros() {
        List<Libro> libros = libroRepository.findAll();

        if (libros.isEmpty()) {
            System.out.println("Aún no se ha registrado ningún libro...\n");
        } else {
            imprimirListaOrdenada(libros, Comparator.comparing(Libro::getTitulo));
        }
    }

    public void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("Aún no se ha registrado ningún autor...\n");
        } else {
            imprimirListaOrdenada(autores, Comparator.comparing(Autor::getNombre));
        }
    }

    public <T> void imprimirListaOrdenada(List<T> lista, Comparator<T> comparador) {
        lista.stream()
                .sorted(comparador)
                .forEach(System.out::println);
    }
}
