package br.com.telematica.siloapi.model;

import br.com.telematica.siloapi.model.enums.RecursoMapEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Modelo de Permissão")
public class PermissaoModel {

	@NotBlank(message = "O campo 'recurso' é obrigatório e não pode estar em branco.")
	@Schema(name = "recurso", description = "Lista dos níveis de permissão. Permissões: BARRAGEM, CANAL, EMPRESA, PENDENCIA, FIRMWARE, LOGGER, MEDICAO, AUDIO, SIRENE, MODULO, USUARIO", example = "BARRAGEM")
	private RecursoMapEnum recurso;

	@NotNull(message = "O campo 'listar' é obrigatório e não pode estar nulo.")
	@Schema(name = "listar", description = "Acessível - 1 / Não Acessível - 0")
	private Integer listar;

	@NotNull(message = "O campo 'buscar' é obrigatório e não pode estar nulo.")
	@Schema(name = "buscar", description = "Acessível - 1 / Não Acessível - 0")
	private Integer buscar;

	@NotNull(message = "O campo 'criar' é obrigatório e não pode estar nulo.")
	@Schema(name = "criar", description = "Acessível - 1 / Não Acessível - 0")
	private Integer criar;

	@NotNull(message = "O campo 'editar' é obrigatório e não pode estar nulo.")
	@Schema(name = "editar", description = "Acessível - 1 / Não Acessível - 0")
	private Integer editar;

	@NotNull(message = "O campo 'deletar' é obrigatório e não pode estar nulo.")
	@Schema(name = "deletar", description = "Acessível - 1 / Não Acessível - 0")
	private Integer deletar;

	public RecursoMapEnum getRecurso() {
		return recurso;
	}

	public void setRecurso(RecursoMapEnum recurso) {
		this.recurso = recurso;
	}

	public Integer getListar() {
		return listar;
	}

	public void setListar(Integer listar) {
		this.listar = listar;
	}

	public Integer getBuscar() {
		return buscar;
	}

	public void setBuscar(Integer buscar) {
		this.buscar = buscar;
	}

	public Integer getCriar() {
		return criar;
	}

	public void setCriar(Integer criar) {
		this.criar = criar;
	}

	public Integer getEditar() {
		return editar;
	}

	public void setEditar(Integer editar) {
		this.editar = editar;
	}

	public Integer getDeletar() {
		return deletar;
	}

	public void setDeletar(Integer deletar) {
		this.deletar = deletar;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PermissaoModel [");
		if (recurso != null) {
			builder.append("recurso=").append(recurso).append(", ");
		}
		if (listar != null) {
			builder.append("listar=").append(listar).append(", ");
		}
		if (buscar != null) {
			builder.append("buscar=").append(buscar).append(", ");
		}
		if (criar != null) {
			builder.append("criar=").append(criar).append(", ");
		}
		if (editar != null) {
			builder.append("editar=").append(editar).append(", ");
		}
		if (deletar != null) {
			builder.append("deletar=").append(deletar);
		}
		builder.append("]");
		return builder.toString();
	}

	public PermissaoModel(@NotBlank RecursoMapEnum recurso, @NotBlank Integer listar, @NotBlank Integer buscar, @NotBlank Integer criar, @NotBlank Integer editar, @NotBlank Integer deletar) {
		super();
		this.recurso = recurso;
		this.listar = listar;
		this.buscar = buscar;
		this.criar = criar;
		this.editar = editar;
		this.deletar = deletar;
	}

	public PermissaoModel() {
		super();
		
	}

}
