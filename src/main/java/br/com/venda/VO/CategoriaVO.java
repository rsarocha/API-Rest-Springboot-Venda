package br.com.venda.VO;

import java.util.List;

import br.com.venda.entity.Produto;

public class CategoriaVO {

	
	private Long codigo;
	private String nome;
	private List<Produto> produto;
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
	public List<Produto> getProduto() {
		return produto;
	}
	public void setProduto(List<Produto> produto) {
		this.produto = produto;
	}

	

}