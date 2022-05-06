package br.edu.ifms.ordem.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.edu.ifms.ordem.services.exceptions.DataBaseException;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
		
	//esse metodo só vai ser utilizado quando for encontrada a exceção EntityNotFoundException no pacote
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandartError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.NOT_FOUND;
		
		//ele tem os elementos que queremos devolver como erro e aqui vamos setar - aqui vamos preparar uma resposta "legal" pra ele
		StandartError error = new StandartError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Recurso não encontrado");		
		//a mensagem que queremos mostrar no front end
		error.setMessage(e.getMessage());
		//caminho onde gerou o erro ou quem solicitou
		error.setPath(request.getRequestURI());
		
		//gera a numeração e tipo de erro
		return ResponseEntity.status(status).body(error);
					
	}
	
	
	//se der um erro, ele é capturado aqui
	@ExceptionHandler(DataBaseException.class)
	public ResponseEntity<StandartError> database(DataBaseException e, HttpServletRequest request){
		
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		//ele tem os elementos que queremos devolver como erro e aqui vamos setar - aqui vamos preparar uma resposta "legal" pra ele
		StandartError error = new StandartError();
		error.setTimestamp(Instant.now());
		error.setStatus(status.value());
		error.setError("Database exception");		
		//a mensagem que queremos mostrar no front end
		error.setMessage(e.getMessage());
		//caminho onde gerou o erro ou quem solicitou
		error.setPath(request.getRequestURI());
		
		//gera a numeração e tipo de erro
		return ResponseEntity.status(status.BAD_REQUEST).body(error);					
	}
	
	
}
