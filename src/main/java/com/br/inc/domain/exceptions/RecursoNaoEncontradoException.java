package com.br.inc.domain.exceptions;

/**
 * Exceção lançada quando um recurso solicitado não é encontrado no sistema.
 */
public class RecursoNaoEncontradoException extends DomainException {

    /**
     * @param message Mensagem detalhando o recurso que não foi encontrado.
     */
    public RecursoNaoEncontradoException(String message) {
        super(message);
    }
}
