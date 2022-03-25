package br.edu.ifms.ordem.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.ifms.ordem.entities.Tecnico;
import br.edu.ifms.ordem.repositories.TecnicoRepository;

@Service
public class TecnicoService {
	
	@Autowired
	private TecnicoRepository repository;
	
	@Transactional(readOnly = true) //importar do spring
	public List<Tecnico> findAll(){	
		return repository.findAll();			
	}
}

