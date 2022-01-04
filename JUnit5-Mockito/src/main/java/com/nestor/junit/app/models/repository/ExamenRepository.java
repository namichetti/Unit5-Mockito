package com.nestor.junit.app.models.repository;

import com.nestor.junit.app.models.Examen;

import java.util.List;

public interface ExamenRepository{

    List<Examen> findAll();
    Examen findById(Long id);
    void guardar(Examen examen);
	Examen findNombre(String string);
}
