package com.br.inc.infrastructure.gateways.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.br.inc.application.gateways.TarefaGateway;
import com.br.inc.domain.entities.Tarefa;
import com.br.inc.infrastructure.persistence.entities.EquipeJpaEntity;
import com.br.inc.infrastructure.persistence.entities.ProjetoJpaEntity;
import com.br.inc.infrastructure.persistence.entities.TarefaJpaEntity;
import com.br.inc.infrastructure.persistence.entities.UsuarioJpaEntity;
import com.br.inc.infrastructure.persistence.repositories.EquipeRepository;
import com.br.inc.infrastructure.persistence.repositories.ProjetoRepository;
import com.br.inc.infrastructure.persistence.repositories.TarefaRepository;
import com.br.inc.infrastructure.persistence.repositories.UsuarioRepository;

@Component
public class TarefaGatewayImpl implements TarefaGateway {

    private final TarefaRepository repository;
    private final ProjetoRepository projetoRepository;
    private final EquipeRepository equipeRepository;
    private final UsuarioRepository usuarioRepository;

    public TarefaGatewayImpl(
            TarefaRepository repository,
            ProjetoRepository projetoRepository,
            EquipeRepository equipeRepository,
            UsuarioRepository usuarioRepository) {
        this.repository = repository;
        this.projetoRepository = projetoRepository;
        this.equipeRepository = equipeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        ProjetoJpaEntity projeto = projetoRepository.getReferenceById(tarefa.getProjeto().getId());
        EquipeJpaEntity equipe = equipeRepository.getReferenceById(tarefa.getEquipeResponsavel().getId());
        UsuarioJpaEntity colaborador = null;
        if (tarefa.getColaboradorResponsavel() != null) {
            colaborador = usuarioRepository.getReferenceById(tarefa.getColaboradorResponsavel().getId());
        }

        TarefaJpaEntity entity = new TarefaJpaEntity(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPrazo(),
                tarefa.getStatus(),
                projeto,
                equipe,
                colaborador
        );
        return repository.save(entity).toDomain();
    }

    @Override
    public Optional<Tarefa> buscarPorId(Long id) {
        return repository.findById(id).map(TarefaJpaEntity::toDomain);
    }

    @Override
    public List<Tarefa> listarTodas() {
        return repository.findAll().stream()
                .map(TarefaJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tarefa> listarPorProjeto(Long projetoId) {
        return repository.findByProjetoId(projetoId).stream()
                .map(TarefaJpaEntity::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deletarPorId(Long id) {
        repository.deleteById(id);
    }
}
