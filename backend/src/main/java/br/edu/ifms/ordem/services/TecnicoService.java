package br.edu.ifms.ordem.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.entities.Tecnico;
import br.edu.ifms.ordem.repositories.TecnicoRepository;
import br.edu.ifms.ordem.services.exceptions.DataBaseException;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	//a assinatura do tecnico define o que ele está devolvendo, o carimbo
	@Transactional(readOnly = true) //importar do spring
	public List<TecnicoDTO> findAll(){	
		List<Tecnico> list = repository.findAll();
//		List<TecnicoDTO> listDto = new ArrayList<>();
		
		//vai fazer uma lista de cada tecnico
//		for (Tecnico t : list) {
//			listDto.add(new TecnicoDTO(t));				
//		}
//		return listDto;		
		//t=varredura
		return list.stream().map(t -> new TecnicoDTO(t)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<TecnicoDTO> findAllPaged(PageRequest pageRequest) {
		Page<Tecnico> list = repository.findAll(pageRequest);
		return list.map(x -> new TecnicoDTO(x));
	}
	
	
	//usa o repository que pesquisa pelo id e depois ele joga dentro do objeto (optional) 
	@Transactional(readOnly = true)//somente consulta
	public TecnicoDTO findById(Long id) {
		Optional<Tecnico> obj = repository.findById(id);
		//aqui vamos interceptar o erro --  orElseThrow:lança a exceção 
		Tecnico entity = obj.orElseThrow(() -> new ResourceNotFoundException("A entidade consultada não foi localizada"));
		return new TecnicoDTO(entity);		
	}

	@Transactional
	public TecnicoDTO insert(TecnicoDTO dto) {
		Tecnico entity = new Tecnico();
		entity.setNome(dto.getNome());
		entity.setTelefone(dto.getTelefone());
		entity.setEmail(dto.getEmail());
		entity.setSenha(dto.getSenha());		
		entity = repository.save(entity);		
		return new TecnicoDTO(entity);		
	}
	

    @Transactional
	public TecnicoDTO update(Long id, TecnicoDTO dto) {
    	try {
    		Tecnico entity = repository.getById(id);    	
    		entity.setNome(dto.getNome());
    		entity.setTelefone(dto.getTelefone());
    		entity.setEmail(dto.getEmail());
    		entity.setSenha(dto.getSenha());		
		 	entity = repository.save(entity);		
		 	return new TecnicoDTO(entity);
    	} catch (EntityNotFoundException e) {
    		throw new ResourceNotFoundException("O recurso com o ID = "+id+" não foi localizado");    		
    	}
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("O recurso com o ID = "+id+" não foi localizado");
			
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não é possível excluir o registro, pois o mesmo está em uso.");
		}				
	}
}
