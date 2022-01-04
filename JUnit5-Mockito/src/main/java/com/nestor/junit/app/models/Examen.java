package com.nestor.junit.app.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class Examen {

    private Long id;
    private String nombre;
    private List<String> preguntas;

    public Examen(){
        this.preguntas = new ArrayList<>();
    }
}
