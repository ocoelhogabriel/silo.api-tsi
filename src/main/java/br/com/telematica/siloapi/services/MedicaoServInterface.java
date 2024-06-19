package br.com.telematica.siloapi.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;

import br.com.telematica.siloapi.model.MedicaoModel;
import br.com.telematica.siloapi.model.dto.MedicaoDTO;
import br.com.telematica.siloapi.model.entity.Medicao;

public interface MedicaoServInterface {

	ResponseEntity<MedicaoDTO> deleteByMsidth(String msidth) throws IOException;

	List<Medicao> findAll() throws IOException;

	ResponseEntity<List<MedicaoDTO>> findAllMedicaoDTO() throws IOException;

	MedicaoDTO findByData(Date date) throws IOException;

	ResponseEntity<MedicaoDTO> save(MedicaoModel medicaoDTO) throws IOException;

	ResponseEntity<MedicaoDTO> update(MedicaoModel medicaoDTO) throws IOException;

}