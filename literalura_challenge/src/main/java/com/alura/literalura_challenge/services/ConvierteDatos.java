package com.alura.literalura_challenge.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos{
    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try{
        return objectMapper.readValue(json, clase);
        }catch(JsonMappingException e){
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T obtenerResultadosDeBusqueda(String json, Class<T> clase){
        try{
            String jsonBusqueda = objectMapper
                    .readTree(json)
                    .get("results").get(0)
                    .toString();
            return obtenerDatos(jsonBusqueda, clase);
        }catch(JsonMappingException e){
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


}
