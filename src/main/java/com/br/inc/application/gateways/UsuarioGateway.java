package com.br.inc.application.gateways;

import java.util.List;
import java.util.Optional;

import com.br.inc.domain.entities.Usuario;

/**
 * Interface de porta de saída para persistência e busca de usuários.
 */
public interface UsuarioGateway {

    /**
     * Salva ou atualiza um usuário.
     *
     * @param usuario Entidade de domínio.
     * @return Usuário persistido.
     */
    Usuario salvar(Usuario usuario);

    /**
     * Busca um usuário pelo CPF.
     *
     * @param cpf CPF formatado ou apenas números.
     * @return Optional contendo o usuário se encontrado.
     */
    Optional<Usuario> buscarPorCpf(String cpf);

    /**
     * Busca um usuário pelo ID.
     *
     * @param id Identificador único.
     * @return Optional contendo o usuário se encontrado.
     */
    Optional<Usuario> buscarPorId(Long id);

    /**
     * Lista todos os usuários cadastrados.
     *
     * @return Lista de usuários.
     */
    List<Usuario> listarTodos();

    /**
     * Remove um usuário pelo ID.
     *
     * @param id Identificador do usuário.
     */
    void deletarPorId(Long id);
}
