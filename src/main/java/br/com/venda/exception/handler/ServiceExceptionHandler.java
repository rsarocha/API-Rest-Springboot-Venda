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

import br.com.venda.VO.ExceptionVO;
import br.com.venda.exception.CategoriaCadastradaException;
import br.com.venda.exception.CodigoCadastradoException;
import br.com.venda.exception.CodigoInvalidoException;
import br.com.venda.exception.NaoLocalizadoException;

@ControllerAdvice
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String HEADER_MESSAGE = "mensagem";
	private static final LocalDate DATE = LocalDate.now();
	private static final String CATEGORIA_JA_CADASTRADA = "Categoria já cadastrada";
	private static final String CODIGO_CADASTRADO = "Código Já Cadastrado";
	private static final String CODIGO_NAO_LOCALIZADO = "Codigo não Localizado";
	private static final String CODIGO_INVALIDO = "Error no Código";
	private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;
//	private static final HttpStatus INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

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
	
	@ExceptionHandler(CodigoInvalidoException.class)
	public ResponseEntity<Object> handleCodigoCadastradoException(CodigoInvalidoException e, ServletWebRequest request) {

		logger.error(e.getMessage());

		ExceptionVO response = criarExceptionResponseVO(DATE, CODIGO_INVALIDO, Arrays.asList(e.getMessage()),
				request.getRequest().getRequestURI());

		HttpHeaders header = new HttpHeaders();
		header.add(HEADER_MESSAGE, e.getMessage());

		return handleExceptionInternal(e, response, header, BAD_REQUEST, request);
	}

	private ExceptionVO criarExceptionResponseVO(LocalDate date, String title, List<String> detail, String instance) {
		ExceptionVO exception = new ExceptionVO();
		exception.setDetail(detail);
		exception.setInstance(instance);
		exception.setTitle(title);
		exception.setTimestamp(date);

		return exception;
	}

}
