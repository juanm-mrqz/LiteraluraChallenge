package com.alura.literalura_challenge.repository;

import com.alura.literalura_challenge.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findAllByIdioma(String idioma);
    List<Libro> findTop5ByOrderByNumeroDeDescargasDesc();
}
