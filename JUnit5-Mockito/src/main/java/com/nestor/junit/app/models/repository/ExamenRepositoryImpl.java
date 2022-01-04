package com.nestor.junit.app.models.repository;

import java.util.List;

import com.nestor.junit.app.models.Examen;

public class ExamenRepositoryImpl implements ExamenRepository {

	@Override
	public List<Examen> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Examen findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void guardar(Examen examen) {
		// TODO Auto-generated method stub

	}

	@Override
	public Examen findNombre(String string) {
		return new Examen(1L,string,null);
	}

}
