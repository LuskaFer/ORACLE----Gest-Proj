package com.br.inc.domain.enums;

/**
 * Define os estados possíveis de um projeto durante seu ciclo de vida.
 */
public enum StatusProjeto {
    /**
     * Projeto em fase de planejamento, ainda não iniciado.
     */
    PLANEJADO,
    /**
     * Projeto com execução iniciada.
     */
    EM_ANDAMENTO,
    /**
     * Projeto finalizado com sucesso.
     */
    CONCLUIDO,
    /**
     * Projeto interrompido definitivamente.
     */
    CANCELADO
}
