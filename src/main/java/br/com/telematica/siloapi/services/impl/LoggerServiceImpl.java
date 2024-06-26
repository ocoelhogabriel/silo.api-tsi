package br.com.telematica.siloapi.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.LoggerModel;
import br.com.telematica.siloapi.model.dto.LoggerDTO;
import br.com.telematica.siloapi.model.entity.LoggerEntity;
import br.com.telematica.siloapi.repository.LoggerRepository;
import br.com.telematica.siloapi.services.LoggerServInterface;
import br.com.telematica.siloapi.utils.Utils;
import br.com.telematica.siloapi.utils.message.MessageResponse;
import jakarta.persistence.EntityNotFoundException;

@Service
public class LoggerServiceImpl implements LoggerServInterface {

	@Autowired
	private LoggerRepository logRepository;

	@Override
	public ResponseEntity<LoggerDTO> save(LoggerModel logModel) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(logModel, "Log model está nulo.");
		Date date = Utils.convertStringToDate(logModel.getData());
		String tipo = logModel.getTipoLogger().toString();
		String mensagem = logModel.getMensagem();

		LoggerEntity entity = new LoggerEntity(date, null, tipo, mensagem);
		LoggerEntity savedEntity = logRepository.save(entity);

		return MessageResponse.success(new LoggerDTO(savedEntity));
	}

	@Override
	public ResponseEntity<List<LoggerDTO>> findByAll() throws EntityNotFoundException, IOException {
		List<LoggerEntity> listAll = logRepository.findAll();

		List<LoggerDTO> loggerDTOs = listAll.stream().map(LoggerDTO::new).toList();

		return MessageResponse.success(loggerDTOs);
	}

	@Override
	public ResponseEntity<Page<LoggerDTO>> findByAllPaginado(Long smocod, String filtro, String startDate, String endDate, @NonNull Pageable pageable) throws EntityNotFoundException, IOException {
		Objects.requireNonNull(pageable, "Pageable está nulo.");

		Specification<LoggerEntity> spec = LoggerEntity.filterByFields(filtro, startDate, endDate);
		Page<LoggerEntity> page = logRepository.findAll(spec, pageable);

		return MessageResponse.success(page.map(LoggerDTO::new));
	}

	@Override
	public ResponseEntity<List<LoggerDTO>> findAllByModulo(Long codigo) {
		Objects.requireNonNull(codigo, "Código do módulo está nulo.");

		List<LoggerEntity> listAll = logRepository.findBySmocod(codigo);

		List<LoggerDTO> loggerDTOs = listAll.stream().map(LoggerDTO::new).toList();

		return MessageResponse.success(loggerDTOs);
	}
}
