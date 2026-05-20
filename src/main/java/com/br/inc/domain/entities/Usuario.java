package com.br.inc.domain.entities;

import java.util.Objects;

import com.br.inc.domain.enums.PerfilUsuario;
import com.br.inc.domain.exceptions.DomainException;

/**
 * Representa um usuário no sistema de gestão de projetos. Entidade rica com
 * validações de integridade no domínio.
 */
public class Usuario {

    private Long id;
    private String nomeCompleto;
    private String cpf;
    private String email;
    private String cargo;
    private String login;
    private String senha;
    private PerfilUsuario perfil;

    /**
     * Construtor completo para criação e reconstituição da entidade.
     *
     * @param nomeCompleto Nome do usuário (obrigatório).
     * @param cpf CPF do usuário (obrigatório - deve ter 11 dígitos).
     * @param email Email válido (obrigatório).
     * @param cargo Cargo ocupado na empresa.
     * @param login Login de acesso.
     * @param senha Senha de acesso.
     * @param perfil Perfil de permissão {@link PerfilUsuario}.
     * @throws DomainException caso alguma validação falhe.
     */
    public Usuario(String nomeCompleto, String cpf, String email, String cargo, String login, String senha, PerfilUsuario perfil) {
        validarCampos(nomeCompleto, cpf, email, login, senha, perfil);
        this.nomeCompleto = nomeCompleto;
        this.cpf = (cpf != null) ? cpf.replaceAll("\\D", "") : null;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    private void validarCampos(String nome, String cpf, String email, String login, String senha, PerfilUsuario perfil) {
        if (nome == null || nome.isBlank()) {
            throw new DomainException("Nome completo é obrigatório.");
        }
        if (cpf == null || cpf.replaceAll("\\D", "").length() != 11) {
            throw new DomainException("CPF deve conter 11 dígitos.");
        }
        if (email == null || !email.contains("@")) {
            throw new DomainException("Email inválido.");
        }
        if (login == null || login.isBlank()) {
            throw new DomainException("Login é obrigatório.");
        }
        if (senha == null || senha.length() < 6) {
            throw new DomainException("Senha deve ter no mínimo 6 caracteres.");
        }
        if (perfil == null) {
            throw new DomainException("Perfil de usuário é obrigatório.");
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getCargo() {
        return cargo;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    /**
     * Atualiza os dados do usuário validando as novas informações.
     */
    public void atualizarDados(String nomeCompleto, String email, String cargo, String login, String senha, PerfilUsuario perfil) {
        validarCampos(nomeCompleto, this.cpf, email, login, senha, perfil);
        this.nomeCompleto = nomeCompleto;
        this.email = email;
        this.cargo = cargo;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    /**
     * Atualiza o perfil do usuário caso possua permissão.
     *
     * @param novoPerfil O novo perfil a ser atribuído.
     */
    public void alternarPerfil(PerfilUsuario novoPerfil) {
        if (novoPerfil == null) {
            throw new DomainException("Novo perfil não pode ser nulo.");
        }
        this.perfil = novoPerfil;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        if (this.id == null || usuario.id == null) {
            return false;
        }
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hashCode(id) : System.identityHashCode(this);
    }
}
