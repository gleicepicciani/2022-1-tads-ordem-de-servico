package br.edu.ifms.ordem.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ifms.ordem.entities.Tecnico;
import br.edu.ifms.ordem.services.TecnicoService;

//controlador - vai processar tudo o q for enviado para a api
@RestController
@RequestMapping(value = "/tecnicos") //padrão do recurso - mapeamento para as requisições
public class TecnicoResource {
	
	@Autowired
	private TecnicoService service;
	
	
	@GetMapping	
	public ResponseEntity<List<Tecnico>> findAll(){//ele empacota 
		List<Tecnico> list = service.findAll();			
		//vai criar um pacote http 
		return ResponseEntity.ok().body(list);
		
		//verbo idepotente - delete, put e get - ele executa só uma vez
	}
	
}
