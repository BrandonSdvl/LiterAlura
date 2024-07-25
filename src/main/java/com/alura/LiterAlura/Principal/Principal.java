package com.alura.LiterAlura.Principal;

import com.alura.LiterAlura.repository.AutorRepository;
import com.alura.LiterAlura.repository.LibroRepository;
import com.alura.LiterAlura.service.Buscador;

import java.util.Scanner;

public class Principal {
    private final Buscador buscador;
    private final EntradaUsuario entradaUsuario;
    private final Scanner teclado = new Scanner(System.in);

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.buscador = new Buscador(libroRepository, autorRepository);
        this.entradaUsuario = new EntradaUsuario(teclado);
    }

    public void mostrarMenu() {
        String opcion = "";
        String menu = """
                Bienvenido/a a LiterAlura
                
                1.- Buscar libro por título
                2.- Listar libros registrados
                3.- Listar autores registrados
                4.- Listar autores vivos en un determinado año
                5.- Listar libros por idioma
                0.- Salir
                
                Seleccione una opción:\s""";

        while(!opcion.equals("0")) {
            System.out.print(menu);

            opcion = teclado.nextLine();

            switch (opcion) {
                case "1" -> buscador.buscarPorTitulo();
                case "2" -> buscador.listarLibros();
                case "3" -> buscador.listarAutores();
                case "4" -> buscador.autoresPorAnio();
                case "5" -> buscador.librosPorIdioma();
                case "0" -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }
}
