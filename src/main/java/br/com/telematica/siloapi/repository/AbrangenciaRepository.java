package br.com.telematica.siloapi.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.telematica.siloapi.model.entity.Abrangencia;

public interface AbrangenciaRepository extends JpaRepository<Abrangencia, Long>, JpaSpecificationExecutor<Abrangencia> {

	Page<Abrangencia> findByAbrcodIn(Pageable pageable, Collection<Long> list);

	Page<Abrangencia> findByAbrnomLike(String nome, Pageable pageable);

	Page<Abrangencia> findByAbrnomLikeAndAbrcodIn(String nome, Pageable pageable, Collection<Long> list);

	List<Abrangencia> findByAbrcodIn(Collection<Long> list);

	Optional<Abrangencia> findByAbrnomLike(String nome);
}
