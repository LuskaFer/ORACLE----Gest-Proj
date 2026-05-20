package com.br.inc.application.usecases;

import com.br.inc.application.dtos.UsuarioResponseDTO;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de Uso responsável por buscar um usuário específico pelo ID.
 */
public class BuscarUsuarioPorIdUseCase {

    private final UsuarioGateway usuarioGateway;

    public BuscarUsuarioPorIdUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * Busca um usuário e retorna seu DTO.
     *
     * @param id Identificador do usuário.
     * @return UsuarioResponseDTO.
     * @throws RecursoNaoEncontradoException se o usuário não existir.
     */
    public UsuarioResponseDTO executar(Long id) {
        return usuarioGateway.buscarPorId(id)
                .map(UsuarioResponseDTO::fromDomain)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));
    }
}
