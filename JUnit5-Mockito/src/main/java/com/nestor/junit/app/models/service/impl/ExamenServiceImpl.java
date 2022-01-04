package com.nestor.junit.app.models.service.impl;

import com.nestor.junit.app.models.Examen;
import com.nestor.junit.app.models.repository.ExamenRepository;
import com.nestor.junit.app.models.service.ExamenService;

import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenRepository repository;

    public ExamenServiceImpl(ExamenRepository repository){
         this.repository = repository;
     }

    @Override
    public Examen findNombre(String nombre) {
        Optional<Examen> examen = repository.findAll()
                .stream()
                .filter((param)->param.getNombre().equals(nombre))
                .findFirst();
        if(examen.isPresent()){
            return examen.orElseThrow();
        }else{
            return null;
        }
    }

	@Override
	public void guardar(Examen examen) {
		this.repository.guardar(examen);
	}
}
