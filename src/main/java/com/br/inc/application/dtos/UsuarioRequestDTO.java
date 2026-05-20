package com.br.inc.application.dtos;

import com.br.inc.domain.enums.PerfilUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para solicitação de criação ou atualização de um usuário.
 */
public record UsuarioRequestDTO(
        @Schema(example = "João da Silva")
        String nomeCompleto,
        @Schema(example = "12345678900")
        String cpf,
        @Schema(example = "joao.silva@empresa.com")
        String email,
        @Schema(example = "Desenvolvedor Backend")
        String cargo,
        @Schema(example = "joao.silva")
        String login,
        @Schema(example = "senha123")
        String senha,
        @Schema(example = "COLABORADOR")
        PerfilUsuario perfil
        ) {

}
