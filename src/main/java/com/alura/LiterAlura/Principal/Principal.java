package com.alura.LiterAlura.Principal;

import com.alura.LiterAlura.model.DatosLibro;
import com.alura.LiterAlura.service.ConsumoAPI;
import com.alura.LiterAlura.service.ConvierteDatos;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Scanner;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com";
    private Scanner teclado = new Scanner(System.in);
    private ConvierteDatos conversor = new ConvierteDatos();

    public void mostrarMenu() {
        var opcion = -1;
        String menu = """
                Bienvenido/a a LiterAlura
                
                1.- Buscar libro por título
                2.- Listar libros registrados
                3.- Listar autores registrados
                4.- Listar autores vivos en un determinado año
                5.- Listar libros por idioma
                6.- Listar libros por idioma
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
        String libro = teclado.nextLine();

        var json = consumoApi.obtenerDatos(URL_BASE + "/books/?search=" + libro.replace(" ", "%20"));

        try {
            JsonNode rootNode = conversor.obtenerDatos(json, JsonNode.class);
            JsonNode resultsNode = rootNode.get("results");

            DatosLibro datosLibro = conversor.obtenerDatos(resultsNode.get(0).toString(), DatosLibro.class);

            System.out.println(datosLibro);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
