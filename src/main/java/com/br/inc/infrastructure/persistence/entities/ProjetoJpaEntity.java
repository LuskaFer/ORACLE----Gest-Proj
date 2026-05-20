package com.br.inc.infrastructure.persistence.entities;

import com.br.inc.domain.entities.Projeto;
import com.br.inc.domain.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "projetos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
