package com.br.inc.application.gateways;

import java.util.List;
import java.util.Optional;

import com.br.inc.domain.entities.Tarefa;

/**
 * Interface de porta de saída para operações de persistência de tarefas.
 */
public interface TarefaGateway {

    Tarefa salvar(Tarefa tarefa);

    Optional<Tarefa> buscarPorId(Long id);

    List<Tarefa> listarTodas();

    List<Tarefa> listarPorProjeto(Long projetoId);

    void deletarPorId(Long id);
}
