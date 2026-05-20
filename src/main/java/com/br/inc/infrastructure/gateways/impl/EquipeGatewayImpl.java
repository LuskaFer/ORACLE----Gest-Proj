package com.br.inc.infrastructure.gateways.impl;

import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.domain.entities.Equipe;
import com.br.inc.infrastructure.persistence.entities.EquipeJpaEntity;
import com.br.inc.infrastructure.persistence.repositories.EquipeRepository;
import java.util.Optional;

public class EquipeGatewayImpl implements EquipeGateway {

    private final EquipeRepository equipeRepository;

    public EquipeGatewayImpl(EquipeRepository equipeRepository) {
        this.equipeRepository = equipeRepository;
    }

    @Override
    public Equipe salvar(Equipe equipe) {
        EquipeJpaEntity entity = EquipeJpaEntity.fromDomain(equipe);
        EquipeJpaEntity saved = equipeRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Equipe> buscarPorId(Long id) {
        return equipeRepository.findById(id).map(EquipeJpaEntity::toDomain);
    }
}
