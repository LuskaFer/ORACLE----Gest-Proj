package com.br.inc.infrastructure.persistence.repositories;

import com.br.inc.infrastructure.persistence.entities.EquipeJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EquipeRepository extends JpaRepository<EquipeJpaEntity, Long> {
}
