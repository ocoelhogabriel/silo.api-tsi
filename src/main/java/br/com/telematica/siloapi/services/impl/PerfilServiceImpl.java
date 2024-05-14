package br.com.telematica.siloapi.services.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import br.com.telematica.siloapi.model.dto.PerfilDTO;
import br.com.telematica.siloapi.model.entity.PerfilEntity;
import br.com.telematica.siloapi.repository.PerfilRepository;
import br.com.telematica.siloapi.services.PerfilInterface;
import jakarta.persistence.EntityNotFoundException;

@Service
public class PerfilServiceImpl implements PerfilInterface {

	@Autowired
	private PerfilRepository perfilRepository;

	@Override
	public List<PerfilDTO> findAll() {
		List<PerfilEntity> perfils = perfilRepository.findAll();
		return perfils.stream().map(perfil -> new PerfilDTO(perfil)).collect(Collectors.toList());
	}

	@Override
	public PerfilDTO findById(@NonNull Long codigo) {
		Objects.requireNonNull(codigo, "Código do Perfil está nulo.");
		Optional<PerfilEntity> perfil = perfilRepository.findById(codigo);
		if (!perfil.isPresent() || perfil.get().getPerdel() == 0) {
			throw new EntityNotFoundException("Perfil não encontrado ou deletado.");
		}
		return new PerfilDTO(perfil.get());
	}

	@Override
	public PerfilEntity save(String desc) {
		return perfilRepository.save(new PerfilEntity(null, desc, 1));
	}

	@Override
	public boolean delete(@NonNull Long perfil) {
		try {
			perfilRepository.deleteById(perfil);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}