package br.com.venda.vo;

import java.io.Serializable;

public class BeanValidationExceptionVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String nomeNulo;
	private String nomeEmBranco;
	private int tamanho;
	private String request;
	private Object fielError;

	public String getNomeNulo() {
		return nomeNulo;
	}

	public void setNomeNulo(String nomeNulo) {
		this.nomeNulo = nomeNulo;
	}

	public String getNomeEmBranco() {
		return nomeEmBranco;
	}

	public void setNomeEmBranco(String nomeEmBranco) {
		this.nomeEmBranco = nomeEmBranco;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public Object getFielError() {
		return fielError;
	}

	public void setFielError(Object fielError) {
		this.fielError = fielError;
	}

}
