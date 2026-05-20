package com.br.inc.infrastructure.persistence.entities;

import java.util.List;
import java.util.stream.Collectors;

import com.br.inc.domain.entities.Equipe;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "equipes")
public class EquipeJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String descricao;

    @ManyToMany
    @JoinTable(
            name = "equipe_membros",
            joinColumns = @JoinColumn(name = "equipe_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<UsuarioJpaEntity> membros;

    public EquipeJpaEntity() {
    }

    public EquipeJpaEntity(Long id, String nome, String descricao, List<UsuarioJpaEntity> membros) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.membros = membros;
    }

    public Equipe toDomain() {
        Equipe equipe = new Equipe(nome, descricao);
        equipe.setId(this.id);
        if (membros != null) {
            membros.forEach(m -> equipe.adicionarMembro(m.toDomain()));
        }
        return equipe;
    }

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

    public List<UsuarioJpaEntity> getMembros() {
        return membros;
    }

    public void setMembros(List<UsuarioJpaEntity> membros) {
        this.membros = membros;
    }

    public static EquipeJpaEntity fromDomain(Equipe equipe) {
        return new EquipeJpaEntity(
                equipe.getId(),
                equipe.getNome(),
                equipe.getDescricao(),
                equipe.getMembros().stream()
                        .map(UsuarioJpaEntity::fromDomain)
                        .collect(Collectors.toList())
        );
    }
}
