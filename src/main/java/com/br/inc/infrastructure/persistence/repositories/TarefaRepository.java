package com.br.inc.infrastructure.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.inc.infrastructure.persistence.entities.TarefaJpaEntity;

public interface TarefaRepository extends JpaRepository<TarefaJpaEntity, Long> {

    List<TarefaJpaEntity> findByProjetoId(Long projetoId);
}
