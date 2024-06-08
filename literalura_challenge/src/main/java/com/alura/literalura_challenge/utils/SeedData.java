package com.alura.literalura_challenge.utils;

import com.alura.literalura_challenge.main.ControladorOpciones;

public class SeedData {
    public static void inyectarDatos(ControladorOpciones consultas){
        String[] titulos = {
                "dickens great",
                "istoria civile del",
                "dickens a tale",
                "don quijote",
                "dorian gray",
                "el paraiso perdido",
                "the sun also rises",
                "sherlock holmes",
                "la divina comedia",
                "railway construction",
                "the great gatsby",
                "la odisea",
                "storia della decadenza",
                "histoire du bas-empire",
                "oliver twist"
        };
        for (String titulo : titulos) {
            consultas.inyectarDatosLibro(titulo);
        }
    }
}
