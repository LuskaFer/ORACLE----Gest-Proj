package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.dtos.TarefaResponseDTO;
import com.br.inc.application.gateways.TarefaGateway;
import com.br.inc.domain.entities.Tarefa;
import com.br.inc.domain.enums.StatusTarefa;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class AtualizarStatusTarefaUseCase {

    private final TarefaGateway tarefaGateway;

    public AtualizarStatusTarefaUseCase(TarefaGateway tarefaGateway) {
        this.tarefaGateway = tarefaGateway;
    }

    public TarefaResponseDTO executar(Long id, StatusTarefa novoStatus) {
        Tarefa tarefa = tarefaGateway.buscarPorId(id)
                .orElseThrow(() -> new DomainException("Tarefa não encontrada com ID: " + id));

        tarefa.atualizarStatus(novoStatus);

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
