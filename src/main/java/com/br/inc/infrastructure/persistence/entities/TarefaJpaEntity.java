package com.br.inc.infrastructure.persistence.entities;

import java.time.LocalDate;

import com.br.inc.domain.entities.Tarefa;
import com.br.inc.domain.enums.StatusTarefa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tarefas")
public class TarefaJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    @Column(nullable = false)
    private LocalDate prazo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusTarefa status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projeto_id", nullable = false)
    private ProjetoJpaEntity projeto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_id", nullable = false)
    private EquipeJpaEntity equipeResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "colaborador_id")
    private UsuarioJpaEntity colaboradorResponsavel;

    public TarefaJpaEntity() {
    }

    public TarefaJpaEntity(Long id, String titulo, String descricao, LocalDate prazo, StatusTarefa status, ProjetoJpaEntity projeto, EquipeJpaEntity equipeResponsavel, UsuarioJpaEntity colaboradorResponsavel) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prazo = prazo;
        this.status = status;
        this.projeto = projeto;
        this.equipeResponsavel = equipeResponsavel;
        this.colaboradorResponsavel = colaboradorResponsavel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public ProjetoJpaEntity getProjeto() {
        return projeto;
    }

    public void setProjeto(ProjetoJpaEntity projeto) {
        this.projeto = projeto;
    }

    public EquipeJpaEntity getEquipeResponsavel() {
        return equipeResponsavel;
    }

    public void setEquipeResponsavel(EquipeJpaEntity equipeResponsavel) {
        this.equipeResponsavel = equipeResponsavel;
    }

    public UsuarioJpaEntity getColaboradorResponsavel() {
        return colaboradorResponsavel;
    }

    public void setColaboradorResponsavel(UsuarioJpaEntity colaboradorResponsavel) {
        this.colaboradorResponsavel = colaboradorResponsavel;
    }

    public Tarefa toDomain() {
        Tarefa tarefa = new Tarefa(
                titulo,
                descricao,
                prazo,
                projeto.toDomain(),
                equipeResponsavel.toDomain(),
                colaboradorResponsavel != null ? colaboradorResponsavel.toDomain() : null
        );
        tarefa.setId(this.id);
        tarefa.atualizarStatus(this.status);
        return tarefa;
    }

    public static TarefaJpaEntity fromDomain(Tarefa tarefa) {
        return new TarefaJpaEntity(
                tarefa.getId(),
                tarefa.getTitulo(),
                tarefa.getDescricao(),
                tarefa.getPrazo(),
                tarefa.getStatus(),
                ProjetoJpaEntity.fromDomain(tarefa.getProjeto()),
                EquipeJpaEntity.fromDomain(tarefa.getEquipeResponsavel()),
                tarefa.getColaboradorResponsavel() != null ? UsuarioJpaEntity.fromDomain(tarefa.getColaboradorResponsavel()) : null
        );
    }
}
