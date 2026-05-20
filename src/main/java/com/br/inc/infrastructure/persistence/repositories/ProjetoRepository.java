package com.br.inc.infrastructure.persistence.repositories;

import com.br.inc.infrastructure.persistence.entities.ProjetoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<ProjetoJpaEntity, Long> {
}
