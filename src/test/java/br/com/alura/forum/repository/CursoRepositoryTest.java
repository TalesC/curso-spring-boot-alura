package br.com.alura.forum.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@ActiveProfiles("test")
class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository repository;
	@Autowired
	private TestEntityManager em;

	@Test
	void deveCarregarUmCursoAoBuscarPeloSeuNome() {
		var nomeCurso = "HTML 5";
		var html5 = new Curso(nomeCurso, "Programação");
		em.persist(html5);
				
		var curso = repository.findByNome(nomeCurso);
		
		assertNotNull(curso);
		assertEquals(nomeCurso, curso.getNome());
	}
	
	@Test
	void naoDeveCarregarUmCursoAoBuscarPeloseuNome() {
		var nomeCurso = "JPA";
		var curso = repository.findByNome(nomeCurso);
		
		assertNull(curso);		
	}
	

}
