package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.exception.ResponseGlobalModel;
import br.com.telematica.siloapi.model.RecursoModel;
import br.com.telematica.siloapi.model.dto.RecursoDTO;
import br.com.telematica.siloapi.services.impl.RecursoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recurso")
@Tag(name = "Recurso", description = "Api para consulta de Recurso")
public class RecursoController extends SecurityRestController {

	@Autowired
	private RecursoServiceImpl recursoServImpl;

	@PostMapping("/v1")
	@Operation(description = "Cadastrar recurso. Obs: Gerado automáticamente ao iniciar a aplicação")
	public ResponseEntity<RecursoDTO> cadastrarRecurso(@RequestBody RecursoModel cadastro) {
		var recursoService = recursoServImpl.save(cadastro);
		return ResponseEntity.ok(recursoService);
	}

	@GetMapping("/v1/{codigo}")
	@Operation(description = "Buscar recurso pelo código")
	public ResponseEntity<RecursoDTO> buscarRecursoPorCodigo(@PathVariable Long codigo) throws EntityNotFoundException, IOException {
		var recursoList = recursoServImpl.findById(codigo);
		return ResponseEntity.ok(recursoList);
	}

	@GetMapping("/v1")
	@Operation(description = "Buscar lista de recurso")
	public ResponseEntity<List<RecursoDTO>> buscarListarRecurso() throws EntityNotFoundException, IOException {
		var recursoList = recursoServImpl.findAll();
		return ResponseEntity.ok(recursoList);
	}

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca de recurso paginado. Obs: O campo 'ordenarPor' requer os seguintes dados: codigo, nome, descricao. Buscar pelo nome do recurso.")
	public ResponseEntity<Page<RecursoDTO>> buscarRecursoPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "nome", required = false) String nome)
			throws EntityNotFoundException, IOException {
		return ResponseEntity.ok(recursoServImpl.findAll(nome, PageRequest.of(pagina, tamanho)));
	}

	@PutMapping("/v1/{codigo}")
	@Operation(description = "Atualizar um recurso. Obs: Como recurso é gerado automaticamente, se alterado pode ocasionar algum problema nas funcionalidades.")
	public ResponseEntity<RecursoDTO> atualizarRecurso(@Valid @PathVariable Long codigo, @Valid @RequestBody RecursoModel entity) throws ParseException {
		var recursoService = recursoServImpl.update(codigo, entity);
		return ResponseEntity.ok(recursoService);
	}

	@DeleteMapping("/v1/{codigo}")
	@Operation(description = "Deletar um recurso. Obs: Como recurso é gerado automaticamente, se alterado pode ocasionar algum problema nas funcionalidades.")
	public ResponseEntity<ResponseGlobalModel> deletarRecurso(@Valid @PathVariable Long codigo) throws IOException {
		var recursoService = recursoServImpl.delete(codigo);
		return ResponseEntity.ok(recursoService);
	}

}