package com.br.inc.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.br.inc.domain.enums.PerfilUsuario;
import com.br.inc.domain.exceptions.DomainException;

class EquipeTest {

    @Test
    @DisplayName("Deve criar uma equipe válida")
    void deveCriarEquipeValida() {
        Equipe equipe = new Equipe("Dev Squad", "Time de desenvolvimento");
        assertNotNull(equipe);
        assertEquals("Dev Squad", equipe.getNome());
        assertTrue(equipe.getMembros().isEmpty());
    }

    @Test
    @DisplayName("Deve adicionar membro com sucesso")
    void deveAdicionarMembro() {
        Equipe equipe = new Equipe("Dev Squad", "Time");
        Usuario usuario = new Usuario("João", "123.456.789-01", "j@email.com", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR);

        equipe.adicionarMembro(usuario);

        assertEquals(1, equipe.getMembros().size());
        assertTrue(equipe.getMembros().contains(usuario));
    }

    @Test
    @DisplayName("Não deve adicionar o mesmo membro duas vezes")
    void naoDeveAdicionarMembroDuplicado() {
        Equipe equipe = new Equipe("Dev Squad", "Time");
        Usuario usuario = new Usuario("João", "123.456.789-01", "j@email.com", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR);

        equipe.adicionarMembro(usuario);

        assertThrows(DomainException.class, () -> equipe.adicionarMembro(usuario));
    }

    @Test
    @DisplayName("Deve remover membro com sucesso")
    void deveRemoverMembro() {
        Equipe equipe = new Equipe("Dev Squad", "Time");
        Usuario usuario = new Usuario("João", "123.456.789-01", "j@email.com", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR);
        equipe.adicionarMembro(usuario);

        equipe.removerMembro(usuario);

        assertTrue(equipe.getMembros().isEmpty());
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover membro inexistente")
    void deveLancarExcecaoAoRemoverInexistente() {
        Equipe equipe = new Equipe("Dev Squad", "Time");
        Usuario usuario = new Usuario("João", "123.456.789-01", "j@email.com", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR);

        assertThrows(DomainException.class, () -> equipe.removerMembro(usuario));
    }
}
