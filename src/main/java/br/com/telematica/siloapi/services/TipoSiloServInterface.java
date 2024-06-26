package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.TipoSiloModel;
import br.com.telematica.siloapi.model.dto.TipoSiloDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

public interface TipoSiloServInterface {

	@Transactional
	public ResponseEntity<TipoSiloDTO> deleteByTsicod(Long codigo) throws IOException;

	public ResponseEntity<TipoSiloDTO> update(Long codigo, TipoSiloModel tipoSiloDTO) throws IOException;

	public ResponseEntity<List<TipoSiloDTO>> findAllTipoSiloDTO() throws IOException;

	public ResponseEntity<TipoSiloDTO> findById(Long id) throws IOException, EmptyResultDataAccessException;

	public ResponseEntity<TipoSiloDTO> save(TipoSiloModel tipoSilo) throws IOException;

	public ResponseEntity<Page<TipoSiloDTO>> tipoSiloFindAllPaginado(String searchTerm, Pageable pageable) throws EntityNotFoundException, IOException;

	List<TipoSiloDTO> sendListAbrangenciaTipoSiloDTO() throws IOException;
}
