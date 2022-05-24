package br.com.venda.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.venda.VO.CategoriaVO;
import br.com.venda.entity.Categoria;
import br.com.venda.repository.CategoriaRepository;

@Service
public class CategoriaService {

	private static Logger logger = LoggerFactory.getLogger(CategoriaService.class);

	@Autowired
	private CategoriaRepository repository;

	public List<CategoriaVO> buscarTodos() {

		logger.info("Buscando todas categorias");

		List<Categoria> entity = repository.findAll();
		return entity.stream().map(novaEntity -> {
			CategoriaVO vo = new CategoriaVO();
			vo.setCodigo(novaEntity.getCodigo());
			vo.setNome(novaEntity.getNome());

			return vo;
		}).collect(Collectors.toList());

	}

	// conversores

	public Categoria converterVOParaEntity(CategoriaVO vo) {

		Categoria entity = new Categoria();
		entity.setCodigo(vo.getCodigo());
		entity.setNome(vo.getNome());
		return entity;

	}

	public CategoriaVO converterEntityParaVO(Categoria entity) {

		CategoriaVO vo = new CategoriaVO();
		vo.setCodigo(entity.getCodigo());
		vo.setNome(entity.getNome());
		return vo;
	}

}
