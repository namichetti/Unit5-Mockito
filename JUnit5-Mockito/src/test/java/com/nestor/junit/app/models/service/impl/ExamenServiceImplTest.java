package com.nestor.junit.app.models.service.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.nestor.junit.app.models.Examen;
import com.nestor.junit.app.models.repository.ExamenRepository;
import com.nestor.junit.app.models.service.ExamenService;

public class ExamenServiceImplTest{


	@Test
	//Podemos usar any para cualquier tipo: anylong(), anyString(), any(CLASE.class) etc...
	void findNombreTest() {
		
		ExamenRepository repository = mock(ExamenRepository.class);
		ExamenService service = new ExamenServiceImpl(repository);
		List<Examen> lista = Arrays.asList(new Examen(1L,"Matematica",null),
                new Examen(2L,"Lengua",null),
                new Examen(3L,"Historia",null));
		
		when(repository.findAll()).thenReturn(lista);
		Examen examen = service.findNombre("Matematica");
		
		assertNotNull(examen);
		assertEquals("Matematica", examen.getNombre());
		
	}
	
	@Test
	void findNombreTest2() {		
		ExamenRepository repository = mock(ExamenRepository.class);	
		ExamenService service = new ExamenServiceImpl(repository);
		Examen examen = service.findNombre("Matematica");
		
		assertNull(examen);
		//Verifico si llamo al método. Da OK
		verify(repository).findAll();
		
	}

	@Test
	void findNombreTest3() {		
		ExamenRepository repository = mock(ExamenRepository.class);	
		//Verifico si llamo al método. FALLA
		verify(repository).findAll();
		
	}
}
