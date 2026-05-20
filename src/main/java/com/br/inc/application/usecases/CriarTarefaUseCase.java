package com.br.inc.application.usecases;

import org.springframework.stereotype.Service;

import com.br.inc.application.dtos.TarefaRequestDTO;
import com.br.inc.application.dtos.TarefaResponseDTO;
import com.br.inc.application.gateways.EquipeGateway;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.application.gateways.TarefaGateway;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Equipe;
import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.entities.Tarefa;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.exceptions.DomainException;

@Service
public class CriarTarefaUseCase {

    private final TarefaGateway tarefaGateway;
    private final ProjetoGateway projetoGateway;
    private final EquipeGateway equipeGateway;
    private final UsuarioGateway usuarioGateway;

    public CriarTarefaUseCase(TarefaGateway tarefaGateway, ProjetoGateway projetoGateway, EquipeGateway equipeGateway, UsuarioGateway usuarioGateway) {
        this.tarefaGateway = tarefaGateway;
        this.projetoGateway = projetoGateway;
        this.equipeGateway = equipeGateway;
        this.usuarioGateway = usuarioGateway;
    }

    public TarefaResponseDTO executar(TarefaRequestDTO request) {
        Projeto projeto = projetoGateway.buscarPorId(request.projetoId())
                .orElseThrow(() -> new DomainException("Projeto não encontrado com ID: " + request.projetoId()));

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

        Tarefa tarefa = new Tarefa(
                request.titulo(),
                request.descricao(),
                request.prazo(),
                projeto,
                equipe,
                colaborador
        );

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
