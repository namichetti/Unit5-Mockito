package com.nestor.junit.app.models.service;

import com.nestor.junit.app.models.Examen;

public interface ExamenService {

    Examen findNombre(String nombre);
    void guardar(Examen examen);
}
