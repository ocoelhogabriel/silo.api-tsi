package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.handler.AbrangenciaHandler;
import br.com.telematica.siloapi.model.SiloModel;
import br.com.telematica.siloapi.model.dto.SiloDTO;
import br.com.telematica.siloapi.model.entity.Silo;
import br.com.telematica.siloapi.repository.SiloRepository;
import br.com.telematica.siloapi.services.SiloServInterface;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class SiloServiceImpl implements SiloServInterface {

	private static final Logger logger = LoggerFactory.getLogger(SiloServiceImpl.class);

	@Autowired
	private SiloRepository siloRepository;

	@Autowired
	private TipoSiloServiceImpl tipoSiloService;

	@Autowired
	private PlantaServiceImpl plantaService;

	@Autowired
	private AbrangenciaHandler abrangenciaHandler;

	@Override
	public ResponseEntity<SiloDTO> save(SiloModel siloModel) throws IOException {
		try {
			var tipoSilo = tipoSiloService.findEntity(siloModel.getTipoSilo());
			var planta = plantaService.findEntity(siloModel.getPlanta());
			var entity = new Silo(null, tipoSilo, siloModel.getNome(), planta, siloModel.getLatitude(), siloModel.getLongitude());
			var result = siloRepository.save(entity);

			logger.info("Silo salvo com sucesso: " + result);
			return MessageResponse.success(new SiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao salvar o Silo: ", e);
			throw new IOException("Erro ao salvar o Silo: " + siloModel, e);
		}
	}

	@Override
	public ResponseEntity<SiloDTO> deleteByPlacod(Long codigo) throws IOException {
		Objects.requireNonNull(codigo, "Código do Silo está nulo.");
		try {
			var silo = findCodigo(codigo);
			siloRepository.delete(silo);
			logger.info("Silo deletado com sucesso: " + silo);
			return MessageResponse.success(null);
		} catch (EntityNotFoundException e) {
			logger.error("Não foi possível encontrar o silo com o ID fornecido: ", e);
			throw new EntityNotFoundException("Não foi possível encontrar o silo com o ID fornecido.", e);
		} catch (Exception e) {
			logger.error("Erro ao deletar o Silo: ", e);
			throw new IOException("Erro ao deletar o Silo: " + codigo, e);
		}
	}

	@Override
	public ResponseEntity<SiloDTO> update(Long codigo, SiloModel siloModel) throws IOException {
		try {
			var tipoSilo = tipoSiloService.findEntity(siloModel.getTipoSilo());
			var planta = plantaService.findEntity(siloModel.getPlanta());
			var entity = new Silo(codigo, tipoSilo, siloModel.getNome(), planta, siloModel.getLatitude(), siloModel.getLongitude());
			var result = siloRepository.save(entity);

			logger.info("Silo atualizado com sucesso: " + result);
			return MessageResponse.success(new SiloDTO(result));
		} catch (Exception e) {
			logger.error("Erro ao atualizar o Silo: ", e);
			throw new IOException("Erro ao atualizar o Silo " + codigo + ": " + siloModel, e);
		}
	}

	@Override
	public List<Silo> findAll() throws IOException {
		return siloRepository.findAll();
	}

	@Override
	public ResponseEntity<List<SiloDTO>> findAllSiloDTO() throws IOException {
		List<SiloDTO> siloDTOList = siloRepository.findAll().stream().map(SiloDTO::new).collect(Collectors.toList());
		return MessageResponse.success(siloDTOList);
	}

	@Override
	public ResponseEntity<SiloDTO> findById(Long codigo) {
		Objects.requireNonNull(codigo, "Código do Silo está nulo.");
		var result = findCodigo(codigo);
		return MessageResponse.success(new SiloDTO(result));
	}

	@Override
	public ResponseEntity<Page<SiloDTO>> siloFindAllPaginado(String searchTerm, Pageable pageable) {
		try {
			var checkSilo = abrangenciaHandler.checkAbrangencia("SILO");
			var checkTipo = abrangenciaHandler.checkAbrangencia("TIPOSILO");
			var checkPlanta = abrangenciaHandler.checkAbrangencia("PLANTA");
			Specification<Silo> spec = Specification.where(null);

			if (checkSilo.isHier() == 0) {
				spec = spec.and(Silo.filterByFields(searchTerm, null, null, null));
			} else {
				spec = spec.and(Silo.filterByFields(searchTerm, checkSilo.listAbrangencia(), checkTipo.isHier() == 0 ? null : checkTipo.listAbrangencia(), checkPlanta.isHier() == 0 ? null : checkPlanta.listAbrangencia()));
			}

			Page<Silo> result = siloRepository.findAll(spec, pageable);
			return ResponseEntity.ok(result.map(SiloDTO::new));
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (IOException ex) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	public Silo findCodigo(Long codigo) {
		return siloRepository.findById(codigo).orElseThrow(() -> new EntityNotFoundException("Silo não encontrado com o ID: " + codigo));
	}

}
