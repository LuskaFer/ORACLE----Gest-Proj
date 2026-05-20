package com.br.inc.application.gateways;

import java.util.List;
import java.util.Optional;

import com.br.inc.domain.entities.Equipe;

/**
 * Interface de porta de saída para operações de persistência de equipes.
 */
public interface EquipeGateway {

    Equipe salvar(Equipe equipe);

    Optional<Equipe> buscarPorId(Long id);

    List<Equipe> listarTodos();

    void deletarPorId(Long id);
}
