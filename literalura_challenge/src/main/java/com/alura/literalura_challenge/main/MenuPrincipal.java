package com.alura.literalura_challenge.main;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {

    public static void mostrarMenu(ControladorOpciones controlador) {
        var option = -1;

        while (option != 0) {
            var menu = """
                    1- Buscar Libro por titulo
                    2- Listar libros buscados
                    3- Listar libros en base a su idioma
                    4- Listar autores buscados
                    5- Consulta de autores vivos segun año
                    6- Consultar top 5 Libros mas descargados

                            
                    0- salir
                    """;
            System.out.println(menu);
            try{
            option = new Scanner(System.in).nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("Asegurese de ingresar un numero valido");
                continue;
            }

            switch (option) {
                case 1:
                    controlador.pedirLibroYGuardar();
                    break;
                case 2:
                    controlador.listarTodosLosLibros();
                    break;
                case 3:
                    controlador.listarLibrosPorIdioma();
                    break;
                case 4:
                    controlador.listadoDeAutoresBuscados();
                    break;
                case 5:
                    controlador.listaAutoresVivos();
                    break;
                case 6:
                    controlador.listarTop5Libros();
                case 0:
                    System.out.println("Cerrando la app...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }


}
