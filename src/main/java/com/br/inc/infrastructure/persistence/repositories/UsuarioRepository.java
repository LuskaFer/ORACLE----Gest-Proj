package com.br.inc.infrastructure.persistence.repositories;

import com.br.inc.infrastructure.persistence.entities.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioJpaEntity, Long> {

    Optional<UsuarioJpaEntity> findByCpf(String cpf);
}
