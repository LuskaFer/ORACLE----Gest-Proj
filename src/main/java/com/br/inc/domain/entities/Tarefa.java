package com.br.inc.domain.entities;

import java.time.LocalDate;
import java.util.Objects;

import com.br.inc.domain.enums.StatusTarefa;
import com.br.inc.domain.exceptions.DomainException;

/**
 * Representa uma tarefa vinculada a um projeto.
 */
public class Tarefa {

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private StatusTarefa status;
    private Projeto projeto;
    private Equipe equipeResponsavel;
    private Usuario colaboradorResponsavel;

    /**
     * Construtor para criação de novas tarefas.
     *
     * @param titulo Título da tarefa (mínimo 3 caracteres).
     * @param descricao Detalhes da tarefa.
     * @param prazo Data limite para conclusão.
     * @param projeto Projeto ao qual a tarefa pertence.
     * @param equipeResponsavel Equipe responsável pela execução.
     * @param colaboradorResponsavel Usuário responsável (opcional).
     */
    public Tarefa(String titulo, String descricao, LocalDate prazo, Projeto projeto, Equipe equipeResponsavel, Usuario colaboradorResponsavel) {
        if (titulo == null || titulo.length() < 3) {
            throw new DomainException("Título da tarefa deve ter ao menos 3 caracteres.");
        }
        if (prazo == null) {
            throw new DomainException("Prazo da tarefa é obrigatório.");
        }
        if (projeto == null) {
            throw new DomainException("Toda tarefa deve estar vinculada a um projeto.");
        }
        if (equipeResponsavel == null) {
            throw new DomainException("Toda tarefa deve ter uma equipe responsável.");
        }
        validarEquipeNoProjeto(projeto, equipeResponsavel);
        validarColaborador(equipeResponsavel, colaboradorResponsavel);

        this.titulo = titulo;
        this.descricao = descricao;
        this.prazo = prazo;
        this.projeto = projeto;
        this.equipeResponsavel = equipeResponsavel;
        this.colaboradorResponsavel = colaboradorResponsavel;
        this.status = StatusTarefa.PENDENTE;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public Equipe getEquipeResponsavel() {
        return equipeResponsavel;
    }

    public Usuario getColaboradorResponsavel() {
        return colaboradorResponsavel;
    }

    /**
     * Atualiza o status da tarefa.
     */
    public void atualizarStatus(StatusTarefa novoStatus) {
        if (novoStatus == null) {
            throw new DomainException("O novo status não pode ser nulo.");
        }
        this.status = novoStatus;
    }

    /**
     * Atualiza os dados da tarefa.
     */
    public void atualizarDados(String titulo, String descricao, LocalDate prazo, Equipe equipeResponsavel, Usuario colaboradorResponsavel) {
        if (titulo == null || titulo.length() < 3) {
            throw new DomainException("Título da tarefa deve ter ao menos 3 caracteres.");
        }
        if (prazo == null) {
            throw new DomainException("Prazo da tarefa é obrigatório.");
        }
        if (equipeResponsavel == null) {
            throw new DomainException("Toda tarefa deve ter uma equipe responsável.");
        }
        validarEquipeNoProjeto(this.projeto, equipeResponsavel);
        validarColaborador(equipeResponsavel, colaboradorResponsavel);
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazo = prazo;
        this.equipeResponsavel = equipeResponsavel;
        this.colaboradorResponsavel = colaboradorResponsavel;
    }

    private void validarColaborador(Equipe equipeResponsavel, Usuario colaboradorResponsavel) {
        if (colaboradorResponsavel == null) {
            return;
        }
        if (!equipeResponsavel.getMembros().contains(colaboradorResponsavel)) {
            throw new DomainException("Colaborador responsável deve pertencer à equipe responsável.");
        }
    }

    private void validarEquipeNoProjeto(Projeto projeto, Equipe equipeResponsavel) {
        if (projeto.getEquipes().stream().noneMatch(equipeResponsavel::equals)) {
            throw new DomainException("Equipe responsável deve estar alocada ao projeto.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tarefa tarefa = (Tarefa) o;
        if (this.id == null || tarefa.id == null) {
            return false;
        }
        return Objects.equals(id, tarefa.id);
    }

    @Override
    public int hashCode() {
        return id != null ? Objects.hashCode(id) : System.identityHashCode(this);
    }
}
