package com.alura.LiterAlura.Principal;

import java.util.Scanner;

public class EntradaUsuario {
    private final Scanner teclado;

    public EntradaUsuario(Scanner teclado) {
        this.teclado = teclado;
    }

    public String obtenerEntradaUsuario(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine();
    }

    public int obtenerEntradaNumerica(String mensaje) {
        int numero;
        while (true) {
            System.out.print(mensaje);
            try {
                numero = Integer.parseInt(teclado.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un valor numérico válido.");
            }
        }
        return numero;
    }
}
