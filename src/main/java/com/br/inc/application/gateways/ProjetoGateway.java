package com.br.inc.application.gateways;

import com.br.inc.domain.entities.Projeto;
import java.util.List;
import java.util.Optional;

/**
 * Interface de porta de saída para operações de persistência de projetos.
 */
public interface ProjetoGateway {

    Projeto salvar(Projeto projeto);

    Optional<Projeto> buscarPorId(Long id);

    List<Projeto> listarTodos();
}
