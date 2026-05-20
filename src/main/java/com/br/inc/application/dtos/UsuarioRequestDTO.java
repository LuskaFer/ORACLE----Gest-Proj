package com.br.inc.application.dtos;

import com.br.inc.domain.enums.PerfilUsuario;

/**
 * DTO para recebimento de dados de novo usuário.
 */
public record UsuarioRequestDTO(
        String nomeCompleto,
        String cpf,
        String email,
        String cargo,
        String login,
        String senha,
        PerfilUsuario perfil
        ) {

}
