package br.com.venda.exception;

public class NaoLocalizadoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NaoLocalizadoException(String msg) {
		super(msg);
	}
}
