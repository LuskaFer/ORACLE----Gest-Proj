package com.br.inc.application.usecases;

import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de Uso responsável por remover um usuário do sistema.
 */
public class DeletarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public DeletarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * Remove um usuário se ele existir.
     *
     * @param id Identificador do usuário.
     */
    public void executar(Long id) {
        if (usuarioGateway.buscarPorId(id).isEmpty()) {
            throw new RecursoNaoEncontradoException("Não foi possível excluir. Usuário não encontrado com o ID: " + id);
        }
        usuarioGateway.deletarPorId(id);
    }
}
