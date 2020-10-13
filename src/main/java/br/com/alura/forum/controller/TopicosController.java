package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	@Autowired
	private CursoRepository cursoRepository;

    @GetMapping
    public List<TopicoDTO> findAll(String nomeCurso){
    	
    	List<Topico> topicos = null;
    	if(nomeCurso == null)
    		topicos = topicoRepository.findAll();
    	else
    		topicos = topicoRepository.findByCurso_Nome(nomeCurso);
    		
        return TopicoDTO.converter(topicos);
    }
    
    @PostMapping
    public ResponseEntity<TopicoDTO> create(@RequestBody TopicoForm form, UriComponentsBuilder uriBuilder) {
    	Topico topico = form.convert(cursoRepository);
    	topicoRepository.save(topico);
    	
    	URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
    	return ResponseEntity.created(uri).body(new TopicoDTO(topico));
    }

}