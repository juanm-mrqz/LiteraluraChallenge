package com.alura.literalura_challenge.model;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name="autores")
public class Autor {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false, unique=true)
    private String nombre ;
    @Column(name="fecha_nacimiento")
    private Integer fechaDeNacimiento;
    @Column(name="fecha_muerte")
    private Integer fechaDeMuerte;
    @OneToMany(mappedBy="autor", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<Libro> libros;



    public Autor() {}

    public Autor(String nombre, Integer fechaDeNacimiento, Integer fechaDeMuerte) {
        this.nombre = nombre;
        this.fechaDeNacimiento = fechaDeNacimiento;
        this.fechaDeMuerte = fechaDeMuerte;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(Integer fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public Integer getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(Integer fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        libros.forEach(libro -> libro.setAutor(this));
        this.libros = libros;
    }

    public Autor(DatosAutor datos) {
        this.nombre = datos.nombre();
        this.fechaDeNacimiento = datos.fechaDeNacimiento();
        this.fechaDeMuerte = datos.fechaDeMuerte();
    }


    @Override
    public String toString() {
        return "Autor{" +
                "id=" + id +
                ", name='" + nombre + '\'' +
                ", birthYear=" + fechaDeNacimiento +
                ", deathYear=" + fechaDeMuerte +
                '}';
    }
}
