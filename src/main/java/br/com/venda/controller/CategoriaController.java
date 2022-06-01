package br.com.venda.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.venda.VO.CategoriaVO;
import br.com.venda.service.CategoriaService;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriaController {

	@Autowired
	private CategoriaService service;

	@GetMapping
	public ResponseEntity<List<CategoriaVO>> findAll() {

		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/{codigo}")
	public ResponseEntity<CategoriaVO> findByCodigo(@PathVariable Long codigo) {

		return ResponseEntity.ok(service.findByCodigo(codigo));

	}

	@PostMapping
	public ResponseEntity<CategoriaVO> save(@RequestBody CategoriaVO categoria) {

		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(categoria));

	}

	@PutMapping("/{codigo}")
	private ResponseEntity<CategoriaVO> update(@PathVariable Long codigo, @RequestBody CategoriaVO categoria) {

		return ResponseEntity.ok(service.update(codigo, categoria));

	}

//	@DeleteMapping("/{codigo}")
//	public ResponseEntity<?> delete(@PathVariable Long codigo) {
//		service.delete(codigo);
//		return ResponseEntity.noContent().build();
//
//	}

}
