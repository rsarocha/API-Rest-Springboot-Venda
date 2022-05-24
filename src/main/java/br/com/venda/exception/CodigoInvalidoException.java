package br.com.venda.exception;

public class CodigoInvalidoException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;
	
	public CodigoInvalidoException (String msg) {
		super(msg);
	}

}
