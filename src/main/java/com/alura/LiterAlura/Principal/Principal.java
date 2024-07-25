package com.alura.LiterAlura.Principal;

import com.alura.LiterAlura.model.Autor;
import com.alura.LiterAlura.model.DatosLibro;
import com.alura.LiterAlura.model.Libro;
import com.alura.LiterAlura.repository.AutorRepository;
import com.alura.LiterAlura.repository.LibroRepository;
import com.alura.LiterAlura.service.ConsumoAPI;
import com.alura.LiterAlura.service.ConvierteDatos;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com";
    private Scanner teclado = new Scanner(System.in);
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void mostrarMenu() {
        var opcion = -1;
        String menu = """
                Bienvenido/a a LiterAlura
                
                1.- Buscar libro por título
                2.- Listar libros registrados
                3.- Listar autores registrados
                4.- Listar autores vivos en un determinado año
                5.- Listar libros por idioma
                0.- Salir
                
                Seleccione una opción:\s""";

        while(opcion != 0) {
            System.out.print(menu);

            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarPorTitulo();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    autoresPorAnio();
                    break;
                case 5:
                    librosPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }

    private void buscarPorTitulo() {
        System.out.print("Ingrese el nombre del libro que desea buscar: ");
        String busqueda = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "/books/?search=" + busqueda.replace(" ", "%20"));

        try {
            JsonNode rootNode = conversor.obtenerDatos(json, JsonNode.class);
            JsonNode resultsNode = rootNode.get("results");

            if(resultsNode.isEmpty()) {
                System.out.println("Libro no encontrado...\n");
                return;
            }

            DatosLibro datosLibro = conversor.obtenerDatos(resultsNode.get(0).toString(), DatosLibro.class);

            Libro libro = new Libro(datosLibro);
            System.out.println(libro);
            libroRepository.save(libro);
        } catch (Exception e) {
            System.err.println("Error al buscar libro: " + e.getMessage());
        }
    }

    private void listarLibros() {
        List<Libro> libros;
        libros = libroRepository.findAll();

        if(libros.isEmpty()) {
            System.out.println("Aún no se ha registrado ningún libro...\n");
            return;
        }

        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }

    private void listarAutores() {
        List<Autor> autores;
        autores = autorRepository.findAll();
        if(autores.isEmpty()) {
            System.out.println("Aún no se ha registrado ningún autor...\n");
            return;
        }
        autores.stream()
                .sorted(Comparator.comparing(Autor::getNombre))
                .forEach(System.out::println);
    }

    private void autoresPorAnio() {
        List<Autor> autores;
        int anio;

        while (true) {
            System.out.print("Ingrese el año a buscar: ");
            try {
                anio = Integer.parseInt(teclado.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor numérico válido.");
            }
        }

        autores = autorRepository.autoresPorFecha(anio);

        if(autores.isEmpty()) {
            System.out.println("No se ha encontrado ningún autor...\n");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void librosPorIdioma() {
        List<String> idiomas = libroRepository.listaDeIdiomas();
        String idiomaSeleccionado;

        if(idiomas.isEmpty()) {
            System.out.println("Aun no hay libros registrados...");
            return;
        }

        do {
            System.out.println("Idiomas disponibles:");
            idiomas.forEach(System.out::println);
            System.out.print("Seleccione uno de los idiomas registrados: ");
            idiomaSeleccionado = teclado.nextLine().toLowerCase();
        } while(!idiomas.contains(idiomaSeleccionado));

        List<Libro> libros = libroRepository.findByIdiomaIgnoreCase(idiomaSeleccionado);
        System.out.println("Libros en idioma '" + idiomaSeleccionado + "':");
        libros.forEach(System.out::println);
    }
}
