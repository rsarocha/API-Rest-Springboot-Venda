package br.com.venda.exception.handler;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.venda.exception.CategoriaCadastradaException;
import br.com.venda.exception.CodigoCadastradoException;
import br.com.venda.exception.CodigoInvalidoException;
import br.com.venda.exception.DeletarException;
import br.com.venda.exception.NaoLocalizadoException;
import br.com.venda.vo.BeanValidationExceptionVO;
import br.com.venda.vo.ExceptionVO;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String HEADER_MESSAGE = "mensagem";
	private static final LocalDate DATE = LocalDate.now();
	private static final String CATEGORIA_JA_CADASTRADA = "Categoria já cadastrada";
	private static final String CODIGO_CADASTRADO = "Código Já Cadastrado";
	private static final String CODIGO_NAO_LOCALIZADO = "Codigo Invalido";
	private static final String CODIGO_INVALIDO = "Código Inválido";
	private static final String DELETANDO = "Deletando Categoria";
	private static final String NOME_NULO = "O nome nao pode ser nulo";
	private static final String NOME_EM_BRANCO = "O nome nao pode ser em branco";
	private static final int TAMANHO = 50;
	private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

	@ExceptionHandler(CategoriaCadastradaException.class)
	public ResponseEntity<Object> handleDadosJaCadastradosException(CategoriaCadastradaException e,
			ServletWebRequest request) {

		logger.error(e.getMessage());

		ExceptionVO response = criarExceptionResponseVO(DATE, CATEGORIA_JA_CADASTRADA, Arrays.asList(e.getMessage()),
				request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
	}

	@ExceptionHandler(CodigoCadastradoException.class)
	public ResponseEntity<Object> handleCodigoCadastradoException(CodigoCadastradoException e,
			ServletWebRequest request) {

		logger.error(e.getMessage());

		ExceptionVO response = criarExceptionResponseVO(DATE, CODIGO_CADASTRADO, Arrays.asList(e.getMessage()),
				request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
	}

	@ExceptionHandler(NaoLocalizadoException.class)
	public ResponseEntity<Object> handleCodigoCadastradoException(NaoLocalizadoException e, ServletWebRequest request) {

		logger.error(e.getMessage());

		ExceptionVO response = criarExceptionResponseVO(DATE, CODIGO_NAO_LOCALIZADO, Arrays.asList(e.getMessage()),
				request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
	}

	@ExceptionHandler(DeletarException.class)
	public ResponseEntity<Object> handleDeletarException(DeletarException e, ServletWebRequest request) {

		logger.error(e.getMessage());

		ExceptionVO response = criarExceptionResponseVO(DATE, DELETANDO, Arrays.asList(e.getMessage()),
				request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
	}

	@ExceptionHandler(CodigoInvalidoException.class)
	public ResponseEntity<Object> handleCodigoCadastradoException(CodigoInvalidoException e,
			ServletWebRequest request) {

		logger.error(e.getMessage());

		ExceptionVO response = criarExceptionResponseVO(DATE, CODIGO_INVALIDO, Arrays.asList(e.getMessage()),
				request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
	}
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<Object> handleException(MethodArgumentNotValidException e,
//			ServletWebRequest request) {
//
//		logger.error(e.getFieldValue(CATEGORIA_JA_CADASTRADA));
//
//		BeanValidationExceptionVO response = criarExceptionBeanValidation(NOME_NULO, NOME_EM_BRANCO,TAMANHO, e.getFieldValue(CATEGORIA_JA_CADASTRADA),
//				request.getRequest().getRequestURI());
//
//		HttpHeaders header = new HttpHeaders();
//		header.add(HEADER_MESSAGE, (String) e.getFieldValue(CATEGORIA_JA_CADASTRADA));
//
//		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
//	}


	private ExceptionVO criarExceptionResponseVO(LocalDate date, String title, List<String> detail, String instance) {
		ExceptionVO exception = new ExceptionVO();
		exception.setDetail(detail);
		exception.setInstance(instance);
		exception.setTitle(title);
		exception.setTimestamp(date);

		return exception;
	}
	
	

	private BeanValidationExceptionVO criarExceptionBeanValidation(String nomeNulo, String nomEmBranco, int tamanho, Object fieldError, String request) {

		BeanValidationExceptionVO vo = new BeanValidationExceptionVO();
		vo.setNomeNulo(nomeNulo);
		vo.setNomeEmBranco(nomEmBranco);	
		vo.setTamanho(tamanho);
		vo.setFielError(fieldError);
		vo.setRequest(request);
		return vo;
	}

}
