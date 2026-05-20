package com.br.inc.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.br.inc.application.dtos.TarefaResponseDTO;
import com.br.inc.application.gateways.TarefaGateway;

@Service
public class ListarTarefasUseCase {

    private final TarefaGateway tarefaGateway;

    public ListarTarefasUseCase(TarefaGateway tarefaGateway) {
        this.tarefaGateway = tarefaGateway;
    }

    public List<TarefaResponseDTO> executar(Long projetoId) {
        if (projetoId != null) {
            return tarefaGateway.listarPorProjeto(projetoId).stream()
                    .map(t -> new TarefaResponseDTO(
                    t.getId(),
                    t.getTitulo(),
                    t.getDescricao(),
                    t.getPrazo(),
                    t.getStatus(),
                    t.getProjeto().getId(),
                    t.getEquipeResponsavel().getId(),
                    t.getColaboradorResponsavel() != null ? t.getColaboradorResponsavel().getId() : null
            ))
                    .collect(Collectors.toList());
        }
        return tarefaGateway.listarTodas().stream()
                .map(t -> new TarefaResponseDTO(
                t.getId(),
                t.getTitulo(),
                t.getDescricao(),
                t.getPrazo(),
                t.getStatus(),
                t.getProjeto().getId(),
                t.getEquipeResponsavel().getId(),
                t.getColaboradorResponsavel() != null ? t.getColaboradorResponsavel().getId() : null
        ))
                .collect(Collectors.toList());
    }
}
