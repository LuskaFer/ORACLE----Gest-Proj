package com.br.inc.infrastructure.persistence.entities;

import com.br.inc.domain.entities.Usuario;
import com.br.inc.domain.enums.PerfilUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entidade JPA para persistência de Usuários.
 */
@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeCompleto;

    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    private String cargo;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilUsuario perfil;

    /**
     * Converte esta entidade JPA para a entidade de domínio.
     */
    public Usuario toDomain() {
        Usuario usuario = new Usuario(nomeCompleto, cpf, email, cargo, login, senha, perfil);
        usuario.setId(this.id);
        return usuario;
    }

    /**
     * Cria uma entidade JPA a partir de uma entidade de domínio.
     */
    public static UsuarioJpaEntity fromDomain(Usuario usuario) {
        return new UsuarioJpaEntity(
                usuario.getId(),
                usuario.getNomeCompleto(),
                usuario.getCpf(),
                usuario.getEmail(),
                usuario.getCargo(),
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getPerfil()
        );
    }
}
