package br.edu.ifms.ordem.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifms.ordem.dto.EquipamentoDTO;
import br.edu.ifms.ordem.services.EquipamentoService;

@RestController
@RequestMapping(value = "/equipamentos")
public class EquipamentoResource {
	
	@Autowired
	private EquipamentoService service;
	
	//para organizar as pesquisas por página
	@GetMapping	
	public ResponseEntity<Page<EquipamentoDTO>> findAllPaged(
			//essa notação garante que passe pela url parametros opcionais
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			//linhas por página
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,			
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy
		){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, 
				Direction.valueOf(direction), orderBy);						
		Page<EquipamentoDTO> list = service.findAllPaged(pageRequest);			
		return ResponseEntity.ok().body(list);		
	}	
	
	@GetMapping(value = "/{id}")
	//vai retornar apenas um equipamento, baseado no id que ele vai entregar 
	public ResponseEntity<EquipamentoDTO> findById(@PathVariable Long id){		
		EquipamentoDTO dto = service.findById(id); //equipamento service
		//resposta baseado no tipo do método
		return ResponseEntity.ok().body(dto);		
	}
	
	@PostMapping
	public ResponseEntity<EquipamentoDTO> insert(@RequestBody EquipamentoDTO dto){
		dto = service.insert(dto);	
		//sessão criada
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);		
	}
	
	//só pode atualizar
		@PutMapping(value = "/{id}")
		public ResponseEntity<EquipamentoDTO> update(@PathVariable Long id, @RequestBody EquipamentoDTO dto){ //@PathVariable passa o valor que está na URL e @RequestBody são os valores que vem no corpo da requisição
			dto = service.update(id, dto);
			//responsável por empacotar todas as respostas
			return ResponseEntity.ok().body(dto);
		}		
		
		//notação pra deletar
		@DeleteMapping(value = "/{id}")
		public ResponseEntity<Void> delete(@PathVariable Long id){
			service.delete(id);
			return ResponseEntity.noContent().build();		
		}	
	
}
