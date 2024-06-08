package com.alura.literalura_challenge.services;

import com.alura.literalura_challenge.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;
}
