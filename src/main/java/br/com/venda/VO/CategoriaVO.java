package br.com.venda.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoriaVO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "O id da Categoria de Risco não pode ser nulo")
	private Long codigo;

	@NotBlank(message = "Não é possível salvar categoria em branco ou nulo")
	@Size(max = 10, message = "Nome não pode ter mais de 10 caracteres")
	private String nome;

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