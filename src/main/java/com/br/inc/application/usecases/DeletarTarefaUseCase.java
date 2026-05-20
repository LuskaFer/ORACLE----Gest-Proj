package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.gateways.TarefaGateway;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class DeletarTarefaUseCase {

    private final TarefaGateway tarefaGateway;

    public DeletarTarefaUseCase(TarefaGateway tarefaGateway) {
        this.tarefaGateway = tarefaGateway;
    }

    public void executar(Long id) {
        tarefaGateway.buscarPorId(id)
                .orElseThrow(() -> new DomainException("Tarefa não encontrada com ID: " + id));
        tarefaGateway.deletarPorId(id);
    }
}
