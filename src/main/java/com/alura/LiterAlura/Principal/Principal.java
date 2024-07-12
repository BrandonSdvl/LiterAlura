package com.alura.LiterAlura.Principal;

import com.alura.LiterAlura.service.ConsumoAPI;

public class Principal {
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com";

    public void mostrarMenu() {
        var json = consumoApi.obtenerDatos(URL_BASE + "/books/");
        System.out.println(json);
    }
}
