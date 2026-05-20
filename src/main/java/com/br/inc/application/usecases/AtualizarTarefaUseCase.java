package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.dtos.TarefaRequestDTO;
import com.br.inc.application.dtos.TarefaResponseDTO;
import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.application.gateways.TarefaGateway;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Equipe;
import com.br.inc.domain.entities.Tarefa;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class AtualizarTarefaUseCase {

    private final TarefaGateway tarefaGateway;
    private final EquipeGateway equipeGateway;
    private final UsuarioGateway usuarioGateway;

    public AtualizarTarefaUseCase(TarefaGateway tarefaGateway, EquipeGateway equipeGateway, UsuarioGateway usuarioGateway) {
        this.tarefaGateway = tarefaGateway;
        this.equipeGateway = equipeGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public TarefaResponseDTO executar(Long id, TarefaRequestDTO request) {
        Tarefa tarefa = tarefaGateway.buscarPorId(id)
                .orElseThrow(() -> new DomainException("Tarefa não encontrada com ID: " + id));

        if (request.equipeId() == null) {
            throw new DomainException("Equipe responsável é obrigatória.");
        }

        Equipe equipe = equipeGateway.buscarPorId(request.equipeId())
                .orElseThrow(() -> new DomainException("Equipe não encontrada com ID: " + request.equipeId()));

        Usuario colaborador = null;
        if (request.colaboradorId() != null) {
            colaborador = usuarioGateway.buscarPorId(request.colaboradorId())
                    .orElseThrow(() -> new DomainException("Usuário não encontrado com ID: " + request.colaboradorId()));
        }

        tarefa.atualizarDados(request.titulo(), request.descricao(), request.prazo(), equipe, colaborador);

        Tarefa tarefaSalva = tarefaGateway.salvar(tarefa);

        return new TarefaResponseDTO(
                tarefaSalva.getId(),
                tarefaSalva.getTitulo(),
                tarefaSalva.getDescricao(),
                tarefaSalva.getPrazo(),
                tarefaSalva.getStatus(),
                tarefaSalva.getProjeto().getId(),
                tarefaSalva.getEquipeResponsavel().getId(),
                tarefaSalva.getColaboradorResponsavel() != null ? tarefaSalva.getColaboradorResponsavel().getId() : null
        );
    }
}
