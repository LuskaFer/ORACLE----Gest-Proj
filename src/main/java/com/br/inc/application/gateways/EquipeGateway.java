package com.br.inc.application.gateways;

import com.br.inc.domain.entities.Equipe;
import java.util.Optional;

/**
 * Interface de porta de saída para operações de persistência de equipes.
 */
public interface EquipeGateway {

    Equipe salvar(Equipe equipe);

    Optional<Equipe> buscarPorId(Long id);
}
