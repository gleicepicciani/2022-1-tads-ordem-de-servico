package br.edu.ifms.ordem.resources;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifms.ordem.entities.Tecnico;

//controlador - vai processar tudo o q for enviado para a api
@RestController
@RequestMapping(value = "/tecnicos") //padrão do recurso - mapeamento para as requisições
public class TecnicoResource {
	
	@GetMapping	
	public ResponseEntity<List<Tecnico>> findAll(){//ele empacota 
		List<Tecnico> list = new ArrayList<>();		
		list.add(new Tecnico(1L, "Ana Souza", "(67) 98746-8942", "ana@gmail.com", "123"));
		list.add(new Tecnico(1L, "Carlos Almeida", "(67) 98978-4542", "carlos@gmail.com", "124"));
		
		//vai criar um pacote http 
		return ResponseEntity.ok().body(null);
		
		//verbo idepotente - delete, put e get - ele executa só uma vez
		
	}
}
