package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;

    @GetMapping("/")
    public List<TopicoDTO> findAll(String nomeCurso){
    	
    	List<Topico> topicos = null;
    	if(nomeCurso == null)
    		topicos = topicoRepository.findAll();
    	else
    		topicos = topicoRepository.findByCurso_Nome(nomeCurso);
    		
        return TopicoDTO.converter(topicos);
    }

}
