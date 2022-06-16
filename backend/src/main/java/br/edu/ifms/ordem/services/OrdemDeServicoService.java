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

import br.edu.ifms.ordem.dto.OrdemDeServicoDTO;
import br.edu.ifms.ordem.entities.OrdemDeServico;
import br.edu.ifms.ordem.repositories.OrdemDeServicoRepository;
import br.edu.ifms.ordem.services.exceptions.DataBaseException;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;

@Service
public class OrdemDeServicoService {
	
	@Autowired
	private OrdemDeServicoRepository repository;
	
	//a assinatura da ordem de servico define o que ele está devolvendo, o carimbo
	@Transactional(readOnly = true) //importar do spring
	public List<OrdemDeServicoDTO> findAll(){	
		List<OrdemDeServico> list = repository.findAll();
		return list.stream().map(o -> new OrdemDeServicoDTO(o)).collect(Collectors.toList());
	}
		
	@Transactional(readOnly = true)
	public Page<OrdemDeServicoDTO> findAllPaged(PageRequest pageRequest) {
		Page<OrdemDeServico> list = repository.findAll(pageRequest);
		return list.map(x -> new OrdemDeServicoDTO(x));
	}
		

		//usa o repository que pesquisa pelo id e depois ele joga dentro do objeto (optional) 
		@Transactional(readOnly = true)//somente consulta
		public OrdemDeServicoDTO findById(Long id) {
			Optional<OrdemDeServico> obj = repository.findById(id);
			//aqui vamos interceptar o erro --  orElseThrow:lança a exceção 
			OrdemDeServico entity = obj.orElseThrow(() -> new ResourceNotFoundException("A ordem de serviço consultada não foi localizada"));
			return new OrdemDeServicoDTO(entity);		
		}
				
		
		@Transactional
		public OrdemDeServicoDTO insert(OrdemDeServicoDTO dto) {
			OrdemDeServico entity = new OrdemDeServico();
			entity.setDescricaoProblema(dto.getDescricaoProblema());
			entity.setDescricaoSolucao(dto.getDescricaoSolucao());
			entity.setDataCadastro(dto.getDataCadastro());
			entity.setStatus(dto.getStatus());
			entity.setPrioridade(dto.getPrioridade());				
			return new OrdemDeServicoDTO(entity);		
		}
		
	    @Transactional
		public OrdemDeServicoDTO update(Long id, OrdemDeServicoDTO dto) {
	    	try {
	    		OrdemDeServico entity = repository.getById(id);  
	    		entity.setDescricaoProblema(dto.getDescricaoProblema());
	    		entity.setDescricaoSolucao(dto.getDescricaoSolucao());
	    		entity.setDataCadastro(dto.getDataCadastro());
	    		entity.setStatus(dto.getStatus());
	    		entity.setPrioridade(dto.getPrioridade());	    				
			 	return new OrdemDeServicoDTO(entity);
	    	} catch (EntityNotFoundException e) {
	    		throw new ResourceNotFoundException("A ordem de serviço com o ID = "+id+" não foi localizada.");	    		
	    	}
		}

		public void delete(Long id) {
			try {
				repository.deleteById(id);			
			} catch (EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException("A ordem de serviço com o ID = "+id+" não foi localizada.");				
			} catch (DataIntegrityViolationException e) {
				throw new DataBaseException("Não é possível excluir o registro, pois o mesmo está em uso.");
			}				
		}
	}

