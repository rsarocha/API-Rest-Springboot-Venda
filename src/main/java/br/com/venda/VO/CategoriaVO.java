package br.com.venda.VO;

import java.io.Serializable;
import java.util.List;

import br.com.venda.entity.Produto;

public class CategoriaVO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long codigo;
	private String nome;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	

}