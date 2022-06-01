package br.com.venda.service;

import java.util.List;
import java.util.Optional;
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
				.orElseThrow(() -> new NaoLocalizadoException("Código não Localizado: " + codigo));

		return converterEntityParaVO(entity);
	}

	public CategoriaVO save(CategoriaVO categoriaVO) {

		verificarExistenciaDeCategoria(categoriaVO);

		logger.info("CADASTRANDO CATEGORIA");
		Categoria save = repository.save(converterVOParaEntity(categoriaVO));
		logger.info("CATEGORIA SALVA COM SUCESSO");

		return converterEntityParaVO(save);
	}

	public CategoriaVO update(Long codigo, CategoriaVO vo) {

		validarParaAtualizar(codigo, vo);

		Optional<Categoria> entity = repository.findByCodigo(codigo);
		entity.get().setCodigo(vo.getCodigo());
		entity.get().setNome(vo.getNome());
		return vo = converterEntityParaVO(repository.save(entity.get()));

	}

//
//	public void delete(Long codigo) {
//
//		verificarParaDeletar(codigo);
//		Optional<Categoria> entity = repository.deleteCodigo(codigo);
//		entity.get().getCodigo();
//
//	}

	// conversores e verificadores

	private void verificarExistenciaDeCategoria(CategoriaVO vo) {

		logger.info("VERIFICANDO EXISTENCIA DE CATEGORIA SALVA NO BANCO");

		if (vo.getCodigo() == null) {
			logger.error("NÃO PODE CADASTRAR CODIGO NULO " + vo.getCodigo());
			throw new CodigoInvalidoException("Nao pode cadastrar codigo da categoria nulo " + vo.getCodigo());
		}

		if (vo.getCodigo() <= 0) {
			logger.error("NÃO PODE CADASTRAR CODIGO DA CATEGORIA MENOR OU IGUAL A ZERO " + vo.getCodigo());
			throw new CodigoInvalidoException(
					"Nao pode cadastrar codigo da categoria menor ou igual a zero " + vo.getCodigo());
		}

		if (repository.findByNome(vo.getNome()).isPresent()) {
			logger.error("JÁ EXISTE CATEGORIA CADASTRADA COM O NOME {}" + vo.getNome() + vo.getCodigo());
			throw new CategoriaCadastradaException("Já Existe categoria cadastrada: " + vo.getNome());
		}

		if (repository.findByCodigo(vo.getCodigo()).isPresent()) {
			logger.error("JÁ EXISTE CATEGORIA CADASTRADA COM O CÓDIGO {} " + vo.getCodigo());
			throw new CodigoCadastradoException("Já Existe categoria cadastrada com o código: " + vo.getCodigo());
		}

	}

	private void validarParaAtualizar(Long codigo, CategoriaVO vo) {

		logger.info("VALIDANDO CODIGO PARA ATUALIZAR CATEGORIA");

		if (vo.getCodigo() == null) {
			logger.error("NÃO PODE ATUALIZAR CÓDIGO NULO " + vo.getCodigo());
			throw new CodigoInvalidoException("Nao pode atualizar código nulo: " + vo.getCodigo());
		}

		if (vo.getCodigo() <= 0) {
			logger.error("NÃO PODE ATUALIZAR CODIGO DA CATEGORIA MENOR OU IGUAL A ZERO " + vo.getCodigo());
			throw new CodigoInvalidoException(
					"Nao pode cadastrar codigo da categoria menor ou igual a zero " + vo.getCodigo());
		}

		if (repository.findByNome(vo.getNome()).isPresent()) {
			logger.error("NÃO PODE ATUALIZAR CATEGORIA CADASTRADA COM O NOME {}" + vo.getNome());
			throw new CategoriaCadastradaException(
					"Não pode atualizar categoria cadastrada com o nome: " + vo.getNome());
		}

	}

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
