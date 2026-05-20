package com.br.inc.application.usecases;

import com.br.inc.application.dtos.ProjetoRequestDTO;
import com.br.inc.application.dtos.ProjetoResponseDTO;
import com.br.inc.application.gateways.ProjetoGateway;
import com.br.inc.application.gateways.UsuarioGateway;
import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.enums.PerfilUsuario;
import com.br.inc.domain.exceptions.DomainException;
import com.br.inc.domain.exceptions.RecursoNaoEncontradoException;

/**
 * Caso de Uso para criação de projetos. Valida se o gerente existe e possui
 * perfil adequado.
 */
public class CriarProjetoUseCase {

    private final ProjetoGateway projetoGateway;
    private final UsuarioGateway usuarioGateway;

    public CriarProjetoUseCase(ProjetoGateway projetoGateway, UsuarioGateway usuarioGateway) {
        this.projetoGateway = projetoGateway;
        this.usuarioGateway = usuarioGateway;
    }

    /**
     * @param dto Dados do projeto.
     * @return Projeto criado.
     * @throws RecursoNaoEncontradoException se o gerente não existir.
     * @throws DomainException se o usuário não for um GERENTE ou ADMINISTRADOR.
     */
    public ProjetoResponseDTO executar(ProjetoRequestDTO dto) {
        Usuario gerente = usuarioGateway.buscarPorId(dto.gerenteId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Gerente não encontrado com ID: " + dto.gerenteId()));

        if (gerente.getPerfil() == PerfilUsuario.COLABORADOR) {
            throw new DomainException("Apenas usuários com perfil GERENTE ou ADMINISTRADOR podem ser responsáveis por projetos.");
        }

        Projeto novoProjeto = new Projeto(
                dto.nome(),
                dto.descricao(),
                dto.dataInicio(),
                dto.dataTerminoPrevista(),
                gerente
        );

        Projeto projetoSalvo = projetoGateway.salvar(novoProjeto);
        return ProjetoResponseDTO.fromDomain(projetoSalvo);
    }
}
