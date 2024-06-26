package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import br.com.telematica.siloapi.model.SiloModuloModel;
import br.com.telematica.siloapi.model.dto.SiloModuloDTO;
import br.com.telematica.siloapi.services.SiloModuloServInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/silo-modulo")
@Tag(name = "Silo Módulo", description = "API para gerenciamento de módulos de silos")
public class SiloModuloController extends SecurityRestController {

	@Autowired
	private SiloModuloServInterface siloModuloServInterface;

	@GetMapping("/v1/listar")
	@Operation(description = "Busca pelos módulos dos silos cadastrados. Retorna uma lista de todos os módulos de silos existentes.")
	public ResponseEntity<List<SiloModuloDTO>> getSiloModulo() {
		return siloModuloServInterface.findAll();
	}

	@PostMapping("/v1/criar")
	@Operation(description = "Cadastro de um novo módulo de silo. Recebe os detalhes do módulo e o armazena no sistema.")
	public ResponseEntity<SiloModuloDTO> createSiloModulo(@RequestBody SiloModuloModel siloModulo) throws IOException {
		return siloModuloServInterface.save(siloModulo);
	}

	@PutMapping("/v1/atualizar/{codigo}")
	@Operation(description = "Atualização de um módulo de silo existente. Atualiza os detalhes de um módulo com base no código fornecido.")
	public ResponseEntity<SiloModuloDTO> updateSiloModulo(@PathVariable("codigo") Long codigo, @RequestBody SiloModuloModel siloModulo) throws IOException {
		return siloModuloServInterface.update(codigo, siloModulo);
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar um módulo de silo pelo código. Remove um módulo específico com base no código fornecido.")
	public ResponseEntity<SiloModuloDTO> deleteSiloModulo(@PathVariable("codigo") Long codigo) throws IOException {
		return siloModuloServInterface.delete(codigo);
	}

	@Operation(description = "Recupera uma lista paginada de objetos SiloModuloDTO com filtragem e ordenação opcionais.")
	@Parameter(name = "filtro", description = "Termo de filtro opcional para buscar Módulos de Silo.")
	@Parameter(name = "pagina", description = "Número da página a ser recuperada, começando em 0.")
	@Parameter(name = "tamanho", description = "Número de itens por página.")
	@Parameter(name = "ordenarPor", description = "Campo pelo qual os resultados serão ordenados. (codigo, silo, descricao, numSerie)")
	@Parameter(name = "direcao", description = "Direção da ordenação, podendo ser ASC (ascendente) ou DESC (descendente).")
	@GetMapping("/v1/paginado")
	public ResponseEntity<Page<SiloModuloDTO>> findAllPaginado(@RequestParam(value = "filtro", required = false) String filtro, @RequestParam(value = "pagina", defaultValue = "0") int pagina, @RequestParam(value = "tamanho", defaultValue = "10") int tamanho,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC") String direcao) throws EntityNotFoundException, IOException {

		Sort sort = Sort.by(Sort.Direction.fromString(direcao), SiloModuloDTO.filtrarDirecao(ordenarPor));
		Pageable pageable = PageRequest.of(pagina, tamanho, sort);

		return siloModuloServInterface.siloModuloFindAllPaginado(filtro, pageable);
	}

}
