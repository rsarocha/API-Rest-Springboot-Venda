package br.com.venda.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.venda.VO.CategoriaVO;
import br.com.venda.entity.Categoria;
import br.com.venda.exception.CategoriaCadastradaException;
import br.com.venda.exception.CodigoCadastradoException;
import br.com.venda.exception.CodigoInvalidoException;
import br.com.venda.exception.NaoLocalizadoException;
import br.com.venda.repository.CategoriaRepository;

@Service
public class CategoriaService {

	private static Logger logger = LoggerFactory.getLogger(CategoriaService.class);

	@Autowired
	private CategoriaRepository repository;

	public List<CategoriaVO> findAll() {

		logger.info("Buscando todas categorias");

		List<Categoria> entity = repository.findAll();
		return entity.stream().map(novaEntity -> {
			CategoriaVO vo = new CategoriaVO();
			vo.setCodigo(novaEntity.getCodigo());
			vo.setNome(novaEntity.getNome());

			return vo;
		}).collect(Collectors.toList());

	}

	public CategoriaVO findByCodigo(Long codigo) {
		logger.info("Buscando categoria por código");

		Categoria entity = repository.findByCodigo(codigo)
				.orElseThrow(() -> new NaoLocalizadoException("Código não encontrado: " + codigo));

		return converterEntityParaVO(entity);
	}

	public CategoriaVO save(CategoriaVO categoriaVO) {

		verificarExistenciaDeCategoria(categoriaVO);

		logger.info("CADASTRANDO CATEGORIA");
		Categoria save = repository.save(converterVOParaEntity(categoriaVO));
		logger.info("CATEGORIA SALVA COM SUCESSO");

		return converterEntityParaVO(save);
	}

	public CategoriaVO update(CategoriaVO vo) {

		validarParaAtualizar(vo);
		repository.save(converterVOParaEntity(vo));

		return vo;
	}

	private void verificarExistenciaDeCategoria(CategoriaVO categoria) {

		logger.info("VERIFICANDO EXISTENCIA DE CATEGORIA SALVA NO BANCO");

		if (repository.findByNome(categoria.getNome()).isPresent()) {
			logger.error("JÁ EXISTE CATEGORIA CADASTRADA COM O NOME {}" + categoria.getNome() + categoria.getCodigo());
			throw new CategoriaCadastradaException("Já Existe categoria cadastrada: " + categoria.getNome());
		}

		if (repository.findByCodigo(categoria.getCodigo()).isPresent()) {
			logger.error("JÁ EXISTE CATEGORIA CADASTRADA COM O CÓDIGO {} " + categoria.getCodigo());
			throw new CodigoCadastradoException(
					"Já Existe categoria cadastrada com o código: " + categoria.getCodigo());
		}

	}

	private void validarParaAtualizar(CategoriaVO vo) {

		logger.info("VALIDANDO CODIG DO INPUT PARA ATUALIZAR CATEGORIA");

		if (vo.getCodigo() == null) {
			logger.error("CÓDIGO INFORMADO É NULO");
			throw new CodigoInvalidoException("O código não pode ser nulo");
		}

		if (vo.getCodigo().compareTo((long) 1) <= 0) {
			logger.error("CODIGO DA CATEGORIA INFORMADO É NEGATIVO OU ZERO");
			throw new CodigoInvalidoException("O código deve ser maior que zero");
		}

		logger.info("ATUALIZAR CATEGORIA: VERIFIFICANDO A EXISTENCIA DE CATEGORIA JÁ CADASTRADA NO BANCO");

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
