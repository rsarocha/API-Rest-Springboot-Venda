package br.com.venda;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import br.com.venda.entity.Categoria;
import br.com.venda.repository.CategoriaRepository;
import br.com.venda.vo.CategoriaVO;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoriaControllerTests {

	static final String URL = "http://localhost:8080";
	private static final String CATEGORIA = "/categorias";
	private static StringBuilder path = new StringBuilder(URL);
	private static final String NOME_CATEGORIA = "Roupas";
	private static final Long CODIGO = 58L;

	@Autowired
	private CategoriaRepository repository;
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@Order(1)
	void esperaSalvarCategoriaComSucesso() {

		CategoriaVO categoriaVO = gerarVOSalvarAtualizar();

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, vo,
				responseType);

		Optional<Categoria> categoria = repository.findByCodigo(response.getBody().getCodigo());

		assertEquals(response.getBody().getCodigo(), categoria.get().getCodigo());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	@Order(2)
	void esperaNaoSalvarCategoriaComMesmoCodigo() {

		CategoriaVO categoriaVO = gerarVOTentarSalvarComErroNoCodigo();

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, vo,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(3)
	void esperaNaoSalvarCategoriaComOMesmoNome() {

		CategoriaVO categoriaVO = gerarVOSalvarAtualizar();

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, vo,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(4)
	void esperaNaoSalvarCategoriaComCodigoMenorOuIgualAZero() {

		CategoriaVO categoriaVO = gerarVOTentarSalvarComErroNoCodigo();

		categoriaVO.setCodigo(-1L);

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, vo,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(5)
	void esperaNaoSalvarCategoriaComCodigoNulo() {

		CategoriaVO categoriaVO = gerarVOTentarSalvarComErroNoCodigo();
		categoriaVO.setCodigo(null);

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.POST, vo,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(6)
	void esperaNaoAtualizarCategoriaComOMesmoNome() {

		Optional<Categoria> entity = repository.findByCodigo(CODIGO);

		path.append("/" + entity.get().getCodigo());

		CategoriaVO categoriaVO = gerarVOSalvarAtualizar();

		categoriaVO.setNome(NOME_CATEGORIA);

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, vo, responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(7)
	void esperaAtualizarCategoria() {

		Optional<Categoria> entity = repository.findByCodigo(CODIGO);

		path.append("/" + entity.get().getCodigo());

		CategoriaVO categoriaVO = gerarVOSalvarAtualizar();
		categoriaVO.setCodigo(CODIGO);
		categoriaVO.setNome("Tenis");

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, vo, responseType);

		Optional<Categoria> categoria = repository.findByCodigo(response.getBody().getCodigo());

		assertEquals(response.getBody().getCodigo(), categoria.get().getCodigo());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Order(8)
	void esperaNaoAtualizarCategoriaCodigoNulo() {

		Optional<Categoria> entity = repository.findByCodigo(CODIGO);

		path.append("/" + entity.get().getCodigo());

		CategoriaVO categoriaVO = gerarVOSalvarAtualizar();
		categoriaVO.setCodigo(null);
		categoriaVO.setNome("Tenis");

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, vo, responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(9)
	void esperaNaoAtualizarCategoriaComCodigoNenorQueZero() {

		Optional<Categoria> entity = repository.findByCodigo(CODIGO);

		path.append("/" + entity.get().getCodigo());

		CategoriaVO categoriaVO = gerarVOSalvarAtualizar();
		categoriaVO.setCodigo(-1L);
		categoriaVO.setNome("Tenis");

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, vo, responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(9)
	void esperaNaoAtualizarCategoriaComCodigoZero() {

		Optional<Categoria> entity = repository.findByCodigo(CODIGO);

		path.append("/" + entity.get().getCodigo());

		CategoriaVO categoriaVO = gerarVOSalvarAtualizar();
		categoriaVO.setCodigo(0L);
		categoriaVO.setNome("Tenis");

		HttpHeaders cabecalho = new HttpHeaders();
		cabecalho.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<CategoriaVO> vo = new HttpEntity<CategoriaVO>(categoriaVO, cabecalho);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = restTemplate.exchange(path.toString(), HttpMethod.PUT, vo, responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Order(10)
	void esperaBuscarCategoriaPorFindAll() {

		List<Categoria> entity = repository.findAll();

		ParameterizedTypeReference<List<CategoriaVO>> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<List<CategoriaVO>> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		assertEquals(response.getBody().getClass(), entity.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(11)
	void esperaBuscarCategoriaPorCodigoValido() {

		Optional<Categoria> entity = repository.findByCodigo(CODIGO);

		path.append("/");
		path.append(entity.get().getCodigo());

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		Optional<Categoria> categoriEntity = repository.findByCodigo(entity.get().getCodigo());

		assertEquals(response.getBody().getCodigo(), categoriEntity.get().getCodigo());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(12)
	void esperaNaoBuscarCategoriaPorCodigoInvalido() {

		path.append("/");
		path.append(999L);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	public CategoriaVO gerarVOSalvarAtualizar() {

		CategoriaVO vo = new CategoriaVO();
		vo.setCodigo(CODIGO);
		vo.setNome(NOME_CATEGORIA);
		return vo;

	}

	public CategoriaVO gerarVOTentarSalvarComErroNoCodigo() {

		CategoriaVO vo = new CategoriaVO();
		vo.setCodigo(CODIGO);

		return vo;

	}

	@BeforeEach
	void inicializar() {
		path.append(CATEGORIA);
	}

	@AfterEach
	void finalizar() {
		path = new StringBuilder(URL);
	}

}
