package com.br.inc.application.dtos;

import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.enums.PerfilUsuario;

/**
 * DTO para retorno de dados de usuário.
 */
public record UsuarioResponseDTO(
        Long id,
        String nomeCompleto,
        String cpf,
        String email,
        String cargo,
        PerfilUsuario perfil
        ) {

    /**
     * Mapeia uma entidade de domínio para o DTO de resposta.
     *
     * @param u Entidade {@link Usuario}
     * @return {@link UsuarioResponseDTO}
     */
    public static UsuarioResponseDTO fromEntity(Usuario u) {
        return new UsuarioResponseDTO(
                u.getId(),
                u.getNomeCompleto(),
                u.getCpf(),
                u.getEmail(),
                u.getCargo(),
                u.getPerfil()
        );
    }
}
