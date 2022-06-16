package br.edu.ifms.ordem.services.exceptions;



//herda de RuntimeException
public class DataBaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public DataBaseException(String msg) {
		super(msg);		 // super é o construtor do RuntimeException
	}
	
	

}
