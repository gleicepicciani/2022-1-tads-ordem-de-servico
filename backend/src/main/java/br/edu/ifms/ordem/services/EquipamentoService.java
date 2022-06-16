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

import br.edu.ifms.ordem.dto.EquipamentoDTO;
import br.edu.ifms.ordem.entities.Equipamento;
import br.edu.ifms.ordem.repositories.EquipamentoRepository;
import br.edu.ifms.ordem.services.exceptions.DataBaseException;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;

@Service
public class EquipamentoService {
	
	@Autowired
	private EquipamentoRepository repository;
	
	@Transactional(readOnly = true) //importar do spring
	public List<EquipamentoDTO> findAll(){	
		List<Equipamento> list = repository.findAll();
		return list.stream().map(e -> new EquipamentoDTO(e)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public Page<EquipamentoDTO> findAllPaged(PageRequest pageRequest) {
		Page<Equipamento> list = repository.findAll(pageRequest);
		return list.map(x -> new EquipamentoDTO(x));
	}
	
	@Transactional(readOnly = true)//somente consulta
	public EquipamentoDTO findById(Long id) {
		Optional<Equipamento> obj = repository.findById(id);
		//aqui vamos interceptar o erro --  orElseThrow:lança a exceção 
		Equipamento entity = obj.orElseThrow(() -> new ResourceNotFoundException("O equipamento consultado não foi localizado"));
		return new EquipamentoDTO(entity);		
	}	
	
	@Transactional
	public EquipamentoDTO insert(EquipamentoDTO dto) {
		Equipamento entity = new Equipamento();
		entity.setEquipamento(dto.getEquipamento());
		entity.setPatrimonio(dto.getPatrimonio());				
		return new EquipamentoDTO(entity);		
	}
	
	@Transactional
	public EquipamentoDTO update(Long id, EquipamentoDTO dto) {
    	try {
    		Equipamento entity = repository.getById(id); 
    		entity.setEquipamento(dto.getEquipamento());
    		entity.setPatrimonio(dto.getPatrimonio());				
    		return new EquipamentoDTO(entity);	
    	} catch (EntityNotFoundException e) {
    		throw new ResourceNotFoundException("O equipamento com o ID = "+id+" não foi localizado.");	    		
    	}
	}	
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);			
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("O equipamento com o ID = "+id+" não foi localizado.");				
		} catch (DataIntegrityViolationException e) {
			throw new DataBaseException("Não é possível excluir o registro, pois o mesmo está em uso.");
		}				
	}
	
	}
	
	