package com.br.inc.domain.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.br.inc.domain.enums.StatusProjeto;
import com.br.inc.domain.exceptions.DomainException;

/**
 * Representa um projeto dentro da organização. Segue regras de negócio para
 * transição de datas e status.
 */
public class Projeto {

    private Long id;
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataTerminoPrevista;
    private StatusProjeto status;
    private Usuario gerente;
    private List<Equipe> equipes;

    /**
     * Construtor para criação de novos projetos.
     *
     * @param nome Nome do projeto (mínimo 3 caracteres).
     * @param descricao Breve descrição do objetivo.
     * @param dataInicio Data de início (não pode ser no passado distante).
     * @param dataTerminoPrevista Data prevista para conclusão (deve ser
     * posterior ao início).
     * @param gerente Usuário responsável com perfil adequado.
     * @throws DomainException caso as datas ou o gerente sejam inválidos.
     */
    public Projeto(String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, Usuario gerente) {
        validarDatas(dataInicio, dataTerminoPrevista);
        if (nome == null || nome.length() < 3) {
            throw new DomainException("Nome do projeto deve ter ao menos 3 caracteres.");
        }
        if (gerente == null) {
            throw new DomainException("Todo projeto deve possuir um gerente responsável.");
        }

        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.gerente = gerente;
        this.status = StatusProjeto.PLANEJADO;
        this.equipes = new ArrayList<>();
    }

    private void validarDatas(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new DomainException("Datas de início e término são obrigatórias.");
        }
        if (fim.isBefore(inicio)) {
            throw new DomainException("A data de término prevista não pode ser anterior à data de início.");
        }
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataTerminoPrevista() {
        return dataTerminoPrevista;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public Usuario getGerente() {
        return gerente;
    }

    /**
     * Retorna uma lista imutável de equipes alocadas para garantir o
     * encapsulamento.
     */
    public List<Equipe> getEquipes() {
        return Collections.unmodifiableList(equipes);
    }

    /**
     * Adiciona uma equipe ao projeto.
     */
    public void adicionarEquipe(Equipe equipe) {
        if (equipe == null) {
            throw new DomainException("Equipe inválida.");
        }
        if (this.equipes.contains(equipe)) {
            throw new DomainException("Esta equipe já está alocada neste projeto.");
        }
        this.equipes.add(equipe);
    }

    /**
     * Remove uma equipe do projeto.
     */
    public void removerEquipe(Equipe equipe) {
        if (!this.equipes.contains(equipe)) {
            throw new DomainException("Esta equipe não está alocada neste projeto.");
        }
        this.equipes.remove(equipe);
    }

    /**
     * Altera o status do projeto seguindo o fluxo de vida permitido.
     *
     * @param novoStatus {@link StatusProjeto}
     */
    public void atualizarStatus(StatusProjeto novoStatus) {
        if (this.status == StatusProjeto.CONCLUIDO || this.status == StatusProjeto.CANCELADO) {
            throw new DomainException("Não é possível alterar o status de um projeto finalizado ou cancelado.");
        }
        this.status = novoStatus;
    }

    /**
     * Atualiza os dados básicos do projeto.
     */
    public void atualizarDados(String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, Usuario gerente) {
        validarDatas(dataInicio, dataTerminoPrevista);
        if (nome == null || nome.length() < 3) {
            throw new DomainException("Nome do projeto deve ter ao menos 3 caracteres.");
        }
        if (gerente == null) {
            throw new DomainException("Todo projeto deve possuir um gerente responsável.");
        }
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.gerente = gerente;
    }

    /**
     * Calcula o percentual de tarefas concluídas para o projeto.
     */
    public double calcularPercentualConcluido(List<Tarefa> tarefas) {
        int total = Optional.ofNullable(tarefas).map(List::size).orElse(0);
        if (total == 0) {
            return 0.0;
        }
        long concluidas = tarefas.stream()
                .filter(t -> t.getStatus() == com.br.inc.domain.enums.StatusTarefa.CONCLUIDA)
                .count();
        return (double) concluidas / total * 100;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Projeto projeto = (Projeto) o;
        if (this.id == null || projeto.id == null) {
            return false;
        }
        return Objects.equals(id, projeto.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hashCode(id) : System.identityHashCode(this);
    }
}
