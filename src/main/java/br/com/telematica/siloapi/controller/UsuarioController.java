package br.com.telematica.siloapi.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.telematica.siloapi.model.UsuarioModel;
import br.com.telematica.siloapi.model.dto.UsuarioDTO;
import br.com.telematica.siloapi.model.dto.UsuarioPermissaoDTO;
import br.com.telematica.siloapi.services.UsuarioServInterface;
import br.com.telematica.siloapi.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/usuario")
@Tag(name = "Usuário", description = "API para gerenciamento de usuários")
public class UsuarioController extends SecurityRestController {

	@Autowired
	private UsuarioServInterface userServImpl;

	@PostMapping("/v1/criar")
	@Operation(description = "Criar um novo usuário. Recebe os detalhes do usuário e o armazena no sistema.")
	public ResponseEntity<UsuarioDTO> criar(@RequestBody @NonNull UsuarioModel cadastro) throws EntityNotFoundException, IOException {
		return userServImpl.saveUpdateEncodePassword(cadastro);
	}

	@GetMapping("/v1/codigo/{codigo}")
	@Operation(description = "Buscar um usuário pelo código. Retorna os detalhes de um usuário específico com base no código fornecido.")
	public ResponseEntity<UsuarioDTO> buscarPorCodigo(@PathVariable @NonNull Long codigo) throws EntityNotFoundException, IOException {
		return userServImpl.findById(codigo);
	}

	@GetMapping("/v1/permissao/{codigo}")
	@Operation(description = "Buscar um usuário e suas permissões pelo código do usuário. Retorna os detalhes do usuário e suas permissões.")
	public ResponseEntity<UsuarioPermissaoDTO> buscarPorCodigoPermissaoUsuario(@PathVariable @NonNull Long codigo) throws EntityNotFoundException, IOException {
		return userServImpl.findByIdPermission(codigo);
	}

	@GetMapping("/v1/lsitar")
	@Operation(description = "Listar todos os usuários cadastrados. Retorna uma lista de todos os usuários existentes.")
	public ResponseEntity<List<UsuarioDTO>> listar() throws EntityNotFoundException, IOException {
		return userServImpl.findAll();
	}

	@GetMapping("/v1/paginado")
	@Operation(description = "Busca paginada de usuários. Retorna uma lista paginada de usuários com opções de filtragem e ordenação. O campo 'ordenarPor' requer os seguintes dados: código, nome, cpf, login, senha, email.")
	public ResponseEntity<Page<UsuarioDTO>> buscarPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina, @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho, @RequestParam(value = "filtro", required = false) String filtro,
			@RequestParam(value = "ordenarPor", defaultValue = "codigo") String ordenarPor, @RequestParam(value = "direcao", defaultValue = "ASC", required = false) String direcao) throws EntityNotFoundException, IOException {
		String ordenarEntity = UsuarioDTO.consultaPagable(ordenarPor);
		if (ordenarEntity == null) {
			return ResponseEntity.badRequest().body(Page.empty());
		}
		return userServImpl.findAll(filtro, Utils.consultaPage(ordenarEntity, direcao, pagina, tamanho));
	}

	@PutMapping("/v1/atualizar/{codigo}")
	@Operation(description = "Atualizar um usuário existente. Atualiza os detalhes de um usuário com base no código fornecido.")
	public ResponseEntity<UsuarioDTO> editar(@Valid @PathVariable @NonNull Long codigo, @Valid @RequestBody @NonNull UsuarioModel entity) throws EntityNotFoundException, IOException {
		return userServImpl.saveUpdateEncodePassword(codigo, entity);
	}

	@DeleteMapping("/v1/deletar/{codigo}")
	@Operation(description = "Deletar um usuário pelo código. Remove um usuário específico com base no código fornecido.")
	public ResponseEntity<UsuarioDTO> deletar(@Valid @PathVariable @NonNull Long codigo) throws IOException {
		return userServImpl.delete(codigo);
	}
}
