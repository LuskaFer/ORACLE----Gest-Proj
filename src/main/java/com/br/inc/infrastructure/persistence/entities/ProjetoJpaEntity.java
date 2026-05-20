package com.br.inc.infrastructure.persistence.entities;

import java.time.LocalDate;

import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.enums.StatusProjeto;

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
@Table(name = "projetos")
public class ProjetoJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataTerminoPrevista;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProjeto status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gerente_id", nullable = false)
    private UsuarioJpaEntity gerente;

    public ProjetoJpaEntity() {
    }

    public ProjetoJpaEntity(Long id, String nome, String descricao, LocalDate dataInicio, LocalDate dataTerminoPrevista, StatusProjeto status, UsuarioJpaEntity gerente) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataTerminoPrevista = dataTerminoPrevista;
        this.status = status;
        this.gerente = gerente;
    }

    // Getters e Setters manuais
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTerminoPrevista() {
        return dataTerminoPrevista;
    }

    public void setDataTerminoPrevista(LocalDate dataTerminoPrevista) {
        this.dataTerminoPrevista = dataTerminoPrevista;
    }

    public StatusProjeto getStatus() {
        return status;
    }

    public void setStatus(StatusProjeto status) {
        this.status = status;
    }

    public UsuarioJpaEntity getGerente() {
        return gerente;
    }

    public void setGerente(UsuarioJpaEntity gerente) {
        this.gerente = gerente;
    }

    public Projeto toDomain() {
        Projeto projeto = new Projeto(nome, descricao, dataInicio, dataTerminoPrevista, gerente.toDomain());
        projeto.setId(this.id);
        projeto.atualizarStatus(this.status);
        return projeto;
    }

    public static ProjetoJpaEntity fromDomain(Projeto projeto) {
        return new ProjetoJpaEntity(
                projeto.getId(),
                projeto.getNome(),
                projeto.getDescricao(),
                projeto.getDataInicio(),
                projeto.getDataTerminoPrevista(),
                projeto.getStatus(),
                UsuarioJpaEntity.fromDomain(projeto.getGerente())
        );
    }
}
