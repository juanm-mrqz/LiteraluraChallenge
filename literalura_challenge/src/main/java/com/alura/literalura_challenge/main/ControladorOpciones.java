package com.alura.literalura_challenge.main;

import com.alura.literalura_challenge.model.Autor;
import com.alura.literalura_challenge.model.DatosLibro;
import com.alura.literalura_challenge.model.Libro;
import com.alura.literalura_challenge.repository.AutorRepository;
import com.alura.literalura_challenge.repository.LibroRepository;
import com.alura.literalura_challenge.services.ConsumoAPI;
import com.alura.literalura_challenge.services.ConvierteDatos;

import java.util.*;

public class ControladorOpciones {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ConsumoAPI consumer = new ConsumoAPI();
    private final ConvierteDatos converter = new ConvierteDatos();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public ControladorOpciones(LibroRepository libroRepository,
                               AutorRepository autorRepository)
    {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    private DatosLibro pedirDatosLibro() {
        System.out.println("Ingresa el nombre del titulo que deseas buscar");
        String tituloDelLibro = new Scanner(System.in).nextLine();
        try{
            var json = consumer
            .obtenerDatos(URL_BASE + "?search="+ tituloDelLibro.replace(" ", "%20"));
            return converter.obtenerResultadosDeBusqueda(json, DatosLibro.class);
        }
        catch(RuntimeException e) {
            return null;
        }
    }

    private void guardarLibroEnDB (DatosLibro datos) {
        Optional<Autor> autorGuardado = autorRepository.findByNombre(datos.autor().get(0).nombre());
        if (autorGuardado.isPresent()) {
            Autor autor = autorGuardado.get();

            Libro libroNuevo = new Libro(datos);
            libroNuevo.setAutor(autor);

            autor.getLibros().add(libroNuevo);
            autorRepository.save(autor);
        }
        else{
            Autor autor = new Autor(
                    datos.autor().get(0).nombre(),
                    datos.autor().get(0).fechaDeNacimiento(),
                    datos.autor().get(0).fechaDeMuerte()
            );
            Libro libro = new Libro(datos);
            libro.setAutor(autor);
            libroRepository.save(libro);
        }
    }
    public void pedirLibroYGuardar(){
        try{
            guardarLibroEnDB(pedirDatosLibro());
        }
        catch(NullPointerException e){
            System.out.println("El libro que deseas buscar no se encuentra disponible");
            System.out.println("Intente realizar otra busqueda\n");

        }
        catch(RuntimeException e){
            System.out.println("El libro ya se encuentra cargado");
        }
    }



    //Listar los libros almacenados en la DB
    public void listarTodosLosLibros() {
        var libros= libroRepository.findAll();
        System.out.println("***** Listado De Libros Buscados *****");

        libros.forEach(this::mostrarInformacionLibro);
    }

    //Listar libros almacenados segun el idioma

    public void listarLibrosPorIdioma() {
        System.out.print("Ingrese el idioma a buscar: ");
        System.out.println("[en]: Ingles, [es]: Español, [fr]: Frances, [it]: Italiano");

        String abbr = new Scanner(System.in).nextLine();
        var libros = libroRepository.findAllByIdioma(abbr);


        String idioma = new Locale(abbr).getDisplayLanguage();
        if(!libros.isEmpty()){
            System.out.printf("***** Listado de libros en %s *****\n", abbr);
            libros.forEach(this::mostrarInformacionLibro);
            System.out.println("\n#################################");
            System.out.printf("Libros en %s encontrados: %s\n",
                    idioma,
                    libros.size());
            System.out.println("#################################\n");
        }
        else {
            System.out.println("\n#################################");
            System.out.println("No se encontraron libros en " + idioma);
            System.out.println("#################################\n");
        }

    }


    public void listadoDeAutoresBuscados() {
        var autores = autorRepository.findAll();
        System.out.println("***** Listado De Autores Buscados *****");
        autores.forEach(this::mostrarInformacionDeAutor);
    }

    public void listaAutoresVivos() {
        System.out.print("Ingrese el año a buscar: ");
        int fechaBuscada = new Scanner(System.in).nextInt();
        var autores = autorRepository.
                findByFechaDeNacimientoLessThanAndFechaDeMuerteGreaterThan(fechaBuscada, fechaBuscada);
        if (!autores.isEmpty()){
            System.out.printf("***** Listado de autores vivos en %s*****\n", fechaBuscada);
            autores.forEach(this::mostrarInformacionDeAutor);
        }
        else{
            System.out.println("No se encontraron autores vivos para el año " + fechaBuscada);
        }
    }



    //Metodo adicional para cargar libros al iniciar
    public void inyectarDatosLibro(String titulo) throws RuntimeException{
        try{

            var json = consumer
                    .obtenerDatos(URL_BASE + "?search="+ titulo.replace(" ", "%20"));
            guardarLibroEnDB(
                    converter
                            .obtenerResultadosDeBusqueda(json, DatosLibro.class)
            );
        }
        catch(RuntimeException e) {
            System.out.println("El libro no se encuentra disponible o ya esta cargado");
        }
    }

    public boolean dbIsEmpty(){
        return this.libroRepository.findAll().isEmpty();
    }
    private void mostrarInformacionLibro(Libro libro){
        System.out.println("==============================");
        System.out.printf("Titulo: %s\n", libro.getTitulo());
        System.out.printf("Autor: %s\n", libro.getAutor().getNombre());
        System.out.printf("Idioma: %s\n", libro.getIdioma());
        System.out.printf("N° descarga: %s\n", libro.getNumeroDeDescargas());
        System.out.println("==============================\n");
    }

    private void mostrarInformacionDeAutor(Autor autor) {
        System.out.println("==============================");
        System.out.printf("Autor: %s\n",autor.getNombre());
        System.out.printf("Año de nacimiento: %s\n",autor.getFechaDeNacimiento());
        System.out.printf("Año de muerte: %s\n",autor.getFechaDeMuerte());
        System.out.println("==============================\n");
    }


    public void listarTop5Libros() {
        System.out.println("***** Top 5 Libros Mas Descargados *****");
        libroRepository
                .findTop5ByOrderByNumeroDeDescargasDesc()
                .forEach(this::mostrarInformacionLibro);
    }
}

