package com.br.inc.infrastructure.gateways.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.domain.entities.Equipe;
import com.br.inc.infrastructure.persistence.entities.EquipeJpaEntity;
import com.br.inc.infrastructure.persistence.repositories.EquipeRepository;

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

    @Override
    public List<Equipe> listarTodos() {
        return equipeRepository.findAll().stream()
                .map(EquipeJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Long id) {
        equipeRepository.deleteById(id);
    }
}
