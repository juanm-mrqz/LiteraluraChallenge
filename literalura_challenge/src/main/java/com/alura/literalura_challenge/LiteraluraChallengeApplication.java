package com.alura.literalura_challenge;

import com.alura.literalura_challenge.main.ControladorOpciones;
import com.alura.literalura_challenge.main.MenuPrincipal;
import com.alura.literalura_challenge.repository.AutorRepository;
import com.alura.literalura_challenge.repository.LibroRepository;
import com.alura.literalura_challenge.utils.SeedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraChallengeApplication implements CommandLineRunner {
	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ControladorOpciones principal = new ControladorOpciones(libroRepository, autorRepository);
		//Si la base de datos esta vacia, carga algunos para empezar a consultar
		if (principal.dbIsEmpty()) {SeedData.inyectarDatos(principal);}
		//Y de todas maneras ejecuta el menu principal
//		for(var l : Locale.getISOLanguages()){
//			Locale.forLanguageTag()
//			System.out.println(new Locale(l).getDisplayLanguage());
//		}
		MenuPrincipal.mostrarMenu(principal);
	}
}
