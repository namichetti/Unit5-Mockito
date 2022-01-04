package com.nestor.junit.app.models.service.impl;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.ArgumentMatchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.mockito.mock.*;
import static org.junit.jupiter.api.Assertions.*;
import com.nestor.junit.app.models.Examen;
import com.nestor.junit.app.models.repository.ExamenRepository;
import com.nestor.junit.app.models.repository.ExamenRepositoryImpl;

//Habilitamos las anotaciones para la inyección
@ExtendWith(MockitoExtension.class)
public class ExamenServiceImplConAnotacionesTest{

	//ESTA CLASE ES IGUAL A LA ANTERIOS, SOLO QUE USAMOS ANOTACIONES PARA LA INYECCIOÓN DE DEPENDENCIA
	
	//Usar isNull() en lugar de null.
	
	@Mock
	private ExamenRepository repository;
	
	// tiene que ser la implementación y no la interfaz.
	@InjectMocks
	private ExamenServiceImpl service;
	
	@Test
	void findNombreTest() {
				List<Examen> lista = Arrays.asList(new Examen(1L,"Matematica",null),
                new Examen(2L,"Lengua",null),
                new Examen(3L,"Historia",null));
		
		when(repository.findAll()).thenReturn(lista);
		Examen examen = service.findNombre("Matematica");	
		assertNotNull(examen);
		assertEquals("Matematica", examen.getNombre());
		
	}
	
	
	
	@Test
	void lanzarExcepcionTest() {
		when(repository.findAll()).thenThrow(NullPointerException.class);
		assertThrows(NullPointerException.class, ()->{
			service.findNombre("Matematica");
		});
	}
	
	@Test
	void lanzarExcepcionTest2() {
		when(repository.findAll()).thenThrow(NullPointerException.class);
		Exception exception = assertThrows(NullPointerException.class, ()->{
			service.findNombre("Matematica");
		});
		
		assertEquals(NullPointerException.class, exception.getClass());
	}
	
	
	//ArgumentMatcher sirve para asegurarse que ciertos argumentos se pasan a los mocks
	//es más robusto y se puede personalizar
	@Test
	void argumentMatcherTest() {
		when(repository.findById(5L)).thenReturn(new Examen(5L,"",null));
		//mockito.argThat()
		//ArgumentMatchers.argThat()
		
		verify(repository).findById(ArgumentMatchers.argThat(argumento->argumento !=null && argumento.equals(5L)));
		

		
	}
	
	//-------------------------------------------------
	@Test
	void argumentMatcherTest2() {
		when(repository.findById(5L)).thenReturn(new Examen(5L,"",null));		
		verify(repository).findById(ArgumentMatchers.argThat(new MiArgsMatcher()));
		
	}
	
	public static class MiArgsMatcher implements ArgumentMatcher<Long>{		
		private Long argument;

		@Override
		public boolean matches(Long argument) {
			this.argument = argument;
			return argument !=null && argument.equals(5L);
		}

		@Override
		public String toString() {
			return "AgumentMantcher aún más personalizado. Argumento: " + argument;
		}
		
	}
	
	//-------------------------------------------------
	
	//La desventaja es que no me deja ver el toString()
	@Test
	void argumentMatcherTest3() {
		when(repository.findById(5L)).thenReturn(new Examen(5L,"",null));		
		verify(repository).findById(ArgumentMatchers.argThat((argumento)->argumento !=null && argumento.equals(5L)));
			
	}
	
	//-------------------------------------------------
	
	//Captura un agumento que se pasan en los métodos mock.
	
	@Captor
	private ArgumentCaptor<Long> captor;
	
	@Test
	void argumentArgumentCapter() {
		when(repository.findById(5L)).thenReturn(new Examen(5L,"",null));	
		
		//ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
		verify(repository).findById(captor.capture());
		assertEquals(5L, captor.getValue());
			
	}
	
	//-------------------------------------------------
	
	//Para lanzar excepciones en métodos VOID
	@Test
	void testDoThrow() {
		Examen examen = new Examen(1L,"",null);
		doThrow(IllegalArgumentException.class).when(repository).guardar(examen);		
		assertThrows(IllegalArgumentException.class, ()->service.guardar(examen));
			
	}
	
	//-------------------------------------------------

	@Test
	void testDoAnswer() {
		when(repository.findNombre("Matematica")).thenReturn(new Examen(5L,"Matematica",null));	
		
		doAnswer(invocation->{
		  Long id = invocation.getArgument(0);
		  return id ==5?true:false;
		  
		}).when(repository).findNombre("Matematica");
		
		Examen examen = service.findNombre("Matematica");	
		assertEquals(5L,examen.getId());
			
	}
	
	//Simulams (al guardar) que se genera un ID correlativo.
		/*@Test
		void testGuardarExamenConAnswer() {
		    Examen newExamen = new Examen(1L,"Química",null);
		 
		    when(repository.guardar(any(Examen.class))).then(new Answer<Examen>() {
		        Long secuencia = 8L;
		        @Override
		        public Examen answer(InvocationOnMock invocation) throws Throwable {
		            Examen examen = invocation.getArgument(0);
		            examen.setId(secuencia++);
		            return examen;
		        }
		    });
		 
		    Examen examen = service.guardar(Datos.EXAMEN);
		 
		    assertNotNull(examen.getId());
		    assertEquals(8L, examen.getId());
		 
		    verify(repository).guardar(any(Examen.class));
		}*/
	
	//-------------------------------------------------
	
	//Llamar a un método real. Por ejemplo cuando no tenemos alternativa...

	@Mock
	private ExamenRepositoryImpl repositoryImpl;
	
	
	@Test
	void testDoCallRealMethod() {
		
		//Tiene que llamar una implementación, no una interfaz, sino da error.
		doCallRealMethod().when(repositoryImpl).findNombre("Matematica");
		Examen examen = service.findNombre("Matematica");	
		assertEquals("Matematica",examen.getNombre());
		
			
	}
	
}
