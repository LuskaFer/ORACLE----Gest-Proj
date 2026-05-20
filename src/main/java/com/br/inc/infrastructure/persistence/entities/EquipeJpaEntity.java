package com.br.inc.infrastructure.persistence.entities;

import com.br.inc.domain.entities.Equipe;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "equipes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Equipe toDomain() {
        Equipe equipe = new Equipe(nome, descricao);
        equipe.setId(this.id);
        if (membros != null) {
            membros.forEach(m -> equipe.adicionarMembro(m.toDomain()));
        }
        return equipe;
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
