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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.venda.VO.CategoriaVO;
import br.com.venda.entity.Categoria;
import br.com.venda.repository.CategoriaRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoriaControllerTests {

	static final String URL = "http://localhost:8080";
	private static final String CATEGORIA = "/categorias";
	private static StringBuilder path = new StringBuilder(URL);

	@Autowired
	private CategoriaRepository repository;
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	@Order(1)
	void buscarCategoria() {

		List<Categoria> entity = repository.findAll();

		path.append("/");

		ParameterizedTypeReference<List<CategoriaVO>> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<List<CategoriaVO>> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		assertEquals(response.getBody().getClass(), entity.getClass());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@Order(2)
	void buscarCategoriaPorCodigoValido() {

		Optional<Categoria> entity = repository.findByCodigo(11L);

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
	@Order(3)
	void buscarCategoriaPorCodigoInvalido() {

		path.append("/");
		path.append(999L);

		ParameterizedTypeReference<CategoriaVO> responseType = new ParameterizedTypeReference<>() {
		};

		ResponseEntity<CategoriaVO> response = this.restTemplate.exchange(path.toString(), HttpMethod.GET, null,
				responseType);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
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
