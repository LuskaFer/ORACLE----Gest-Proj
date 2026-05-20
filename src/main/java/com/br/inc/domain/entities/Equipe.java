package com.br.inc.domain.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.br.inc.domain.exceptions.DomainException;

/**
 * Representa uma equipe de trabalho composta por múltiplos usuários.
 */
public class Equipe {

    private Long id;
    private String nome;
    private String descricao;
    private List<Usuario> membros;

    /**
     * @param nome Nome da equipe (obrigatório).
     * @param descricao Descrição do propósito da equipe.
     */
    public Equipe(String nome, String descricao) {
        if (nome == null || nome.isBlank()) {
            throw new DomainException("Nome da equipe é obrigatório.");
        }
        this.nome = nome;
        this.descricao = descricao;
        this.membros = new ArrayList<>();
    }

    // Getters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna uma lista imutável de membros para garantir o encapsulamento.
     *
     * @return List de {@link Usuario}
     */
    public List<Usuario> getMembros() {
        return Collections.unmodifiableList(membros);
    }

    /**
     * Adiciona um novo membro à equipe.
     *
     * @param usuario Membro a ser adicionado.
     * @throws DomainException caso o usuário já faça parte da equipe.
     */
    public void adicionarMembro(Usuario usuario) {
        if (usuario == null) {
            throw new DomainException("Usuário inválido.");
        }
        if (this.membros.contains(usuario)) {
            throw new DomainException("Este usuário já é membro desta equipe.");
        }
        this.membros.add(usuario);
    }

    /**
     * Remove um membro da equipe.
     *
     * @param usuario Membro a ser removido.
     */
    public void removerMembro(Usuario usuario) {
        if (!this.membros.contains(usuario)) {
            throw new DomainException("Este usuário não é membro desta equipe.");
        }
        this.membros.remove(usuario);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Equipe equipe = (Equipe) o;
        if (this.id == null || equipe.id == null) {
            return false;
        }
        return Objects.equals(id, equipe.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hashCode(id) : System.identityHashCode(this);
    }
}
