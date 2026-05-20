package com.br.inc.domain.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.br.inc.domain.enums.PerfilUsuario;
import com.br.inc.domain.exceptions.DomainException;

class UsuarioTest {

    @Test
    @DisplayName("Deve criar um usuário válido com todos os campos")
    void deveCriarUsuarioValido() {
        Usuario usuario = new Usuario(
                "João da Silva",
                "123.456.789-01",
                "joao@email.com",
                "Desenvolvedor",
                "joao.silva",
                "senha123",
                // PerfilUsuario
                PerfilUsuario.COLABORADOR
        );

        assertNotNull(usuario);
        assertEquals("João da Silva", usuario.getNomeCompleto());
        assertEquals("12345678901", usuario.getCpf().replaceAll("\\D", ""));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com nome vazio")
    void deveLancarExcecaoNomeVazio() {
        assertThrows(DomainException.class, () -> new Usuario(
                "", "123.456.789-01", "joao@email.com", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR
        ));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com CPF inválido")
    void deveLancarExcecaoCpfInvalido() {
        assertThrows(DomainException.class, () -> new Usuario(
                "João", "123", "joao@email.com", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR
        ));
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar usuário com email inválido")
    void deveLancarExcecaoEmailInvalido() {
        assertThrows(DomainException.class, () -> new Usuario(
                "João", "123.456.789-01", "email_sem_arroba", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR
        ));
    }

    @Test
    @DisplayName("Deve atualizar os dados do usuário corretamente")
    void deveAtualizarDadosUsuario() {
        Usuario usuario = new Usuario(
                "João", "123.456.789-01", "joao@email.com", "Dev", "login", "senha123", PerfilUsuario.COLABORADOR
        );

        usuario.atualizarDados("João Silva", "joao.novo@email.com", "Sênior", "joao.login", "novasenha", PerfilUsuario.GERENTE);

        assertEquals("João Silva", usuario.getNomeCompleto());
        assertEquals("joao.novo@email.com", usuario.getEmail());
        assertEquals("Sênior", usuario.getCargo());
        assertEquals(PerfilUsuario.GERENTE, usuario.getPerfil());
    }
}
