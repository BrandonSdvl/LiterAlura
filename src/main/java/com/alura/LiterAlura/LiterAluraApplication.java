package com.alura.LiterAlura;

import com.alura.LiterAlura.Principal.Principal;
import com.alura.LiterAlura.repository.AutorRepository;
import com.alura.LiterAlura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiterAluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiterAluraApplication.class, args);
	}

	@Override
	public void run(String ...args) {
		Principal main = new Principal(libroRepository, autorRepository);
		main.mostrarMenu();
	}
}
