package com.br.inc.application.usecases;

import com.br.inc.application.dtos.UsuarioRequestDTO;
import com.br.inc.application.dtos.UsuarioResponseDTO;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de Uso responsável por atualizar os dados de um usuário existente.
 */
public class AtualizarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public AtualizarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * Executa a atualização de um usuário existente.
     *
     * @param id Identificador do usuário.
     * @param dto Novos dados.
     * @return UsuarioResponseDTO atualizado.
     */
    public UsuarioResponseDTO executar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioGateway.buscarPorId(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário não encontrado com o ID: " + id));

        usuario.atualizarDados(
                dto.nomeCompleto(),
                dto.email(),
                dto.cargo(),
                dto.login(),
                dto.senha(),
                dto.perfil()
        );

        Usuario atualizado = usuarioGateway.salvar(usuario);
        return UsuarioResponseDTO.fromDomain(atualizado);
    }
}
