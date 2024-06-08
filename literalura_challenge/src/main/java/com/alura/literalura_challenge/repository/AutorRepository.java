package com.alura.literalura_challenge.repository;

import com.alura.literalura_challenge.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombre (String name);

    List<Autor> findByFechaDeNacimientoLessThanAndFechaDeMuerteGreaterThan(int fechaInicial, int fechaFinal);

}
