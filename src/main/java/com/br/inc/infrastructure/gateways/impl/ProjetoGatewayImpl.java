package com.br.inc.infrastructure.gateways.impl;

import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.domain.entities.Projeto;
import com.br.inc.infrastructure.persistence.entities.ProjetoJpaEntity;
import com.br.inc.infrastructure.persistence.repositories.ProjetoRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProjetoGatewayImpl implements ProjetoGateway {

    private final ProjetoRepository projetoRepository;

    public ProjetoGatewayImpl(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    @Override
    public Projeto salvar(Projeto projeto) {
        ProjetoJpaEntity entity = ProjetoJpaEntity.fromDomain(projeto);
        ProjetoJpaEntity saved = projetoRepository.save(entity);
        return saved.toDomain();
    }

    @Override
    public Optional<Projeto> buscarPorId(Long id) {
        return projetoRepository.findById(id).map(ProjetoJpaEntity::toDomain);
    }

    @Override
    public List<Projeto> listarTodos() {
        return projetoRepository.findAll().stream()
                .map(ProjetoJpaEntity::toDomain)
                .collect(Collectors.toList());
    }
}
