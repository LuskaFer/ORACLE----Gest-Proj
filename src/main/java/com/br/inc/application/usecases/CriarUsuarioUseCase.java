package com.br.inc.application.usecases;

import com.br.inc.application.dtos.UsuarioRequestDTO;
import com.br.inc.application.dtos.UsuarioResponseDTO;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.exceptions.DomainException;

/**
 * Caso de Uso responsável pela criação de novos usuários.
 */
public class CriarUsuarioUseCase {

    private final UsuarioGateway usuarioGateway;

    public CriarUsuarioUseCase(UsuarioGateway usuarioGateway) {
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * Executa a regra de negócio para criar um usuário. Valida se o CPF já
     * existe antes de persistir.
     *
     * @param dto Dados vindos da camada de Presentation.
     * @return DTO de resposta com os dados persistidos.
     * @throws DomainException caso o CPF já esteja cadastrado.
     */
    public UsuarioResponseDTO executar(UsuarioRequestDTO dto) {
        if (usuarioGateway.buscarPorCpf(dto.cpf()).isPresent()) {
            throw new DomainException("Já existe um usuário cadastrado com este CPF.");
        }

        Usuario novoUsuario = new Usuario(
                dto.nomeCompleto(),
                dto.cpf(),
                dto.email(),
                dto.cargo(),
                dto.login(),
                dto.senha(),
                dto.perfil()
        );

        Usuario usuarioSalvo = usuarioGateway.salvar(novoUsuario);
        return UsuarioResponseDTO.fromDomain(usuarioSalvo);
    }
}
