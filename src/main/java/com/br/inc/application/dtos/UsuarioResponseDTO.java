package com.br.inc.application.dtos;

import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.enums.PerfilUsuario;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para retorno de dados de usuário.
 */
public record UsuarioResponseDTO(
        @Schema(example = "1")
        Long id,
        @Schema(example = "João da Silva")
        String nomeCompleto,
        @Schema(example = "123.456.789-00")
        String cpf,
        @Schema(example = "joao.silva@empresa.com")
        String email,
        @Schema(example = "Desenvolvedor Backend")
        String cargo,
        @Schema(example = "joao.silva")
        String login,
        @Schema(example = "COLABORADOR")
        PerfilUsuario perfil
        ) {

    /**
     * Mapeia uma entidade de domínio para o DTO de resposta.
     *
     * @param u Entidade {@link Usuario}
     * @return {@link UsuarioResponseDTO}
     */
    public static UsuarioResponseDTO fromDomain(Usuario u) {
        return new UsuarioResponseDTO(
                u.getId(),
                u.getNomeCompleto(),
                u.getCpf(),
                u.getEmail(),
                u.getCargo(),
                u.getLogin(),
                u.getPerfil()
        );
    }
}
