package com.br.inc.application.usecases;

import java.util.List;
import java.util.stream.Collectors;

import com.br.inc.application.dtos.UsuarioResponseDTO;
import com.br.inc.application.gateways.UsuarioGateway;

/**
 * Caso de Uso responsável por listar todos os usuários.
 */
public class ListarUsuariosUseCase {

    private final UsuarioGateway usuarioGateway;

    public ListarUsuariosUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * Retorna a lista de todos os usuários cadastrados.
     *
     * @return Lista de UsuarioResponseDTO.
     */
    public List<UsuarioResponseDTO> executar() {
        return usuarioGateway.listarTodos().stream()
                .map(UsuarioResponseDTO::fromDomain)
                .collect(Collectors.toList());
    }
}
