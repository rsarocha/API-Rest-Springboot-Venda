package br.com.venda.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.venda.entity.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

	Optional<Categoria> findByNome(String nome);

	Optional<Categoria> findByCodigo(Long codigo);

	

	

}
