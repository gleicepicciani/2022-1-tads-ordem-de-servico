package br.edu.ifms.ordem.services.exceptions;


//herda de RuntimeException
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public ResourceNotFoundException(String msg) {
		super(msg);		 // super é o construtor do RuntimeException
	}
	
	

}
