package com.rafaelsanzio.votacao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroResponse> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(SessaoFechadaException.class)
    public ResponseEntity<ErroResponse> handleSessaoFechada(SessaoFechadaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(VotoDuplicadoException.class)
    public ResponseEntity<ErroResponse> handleVotoDuplicado(VotoDuplicadoException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(SessaoAindaAbertaException.class)
    public ResponseEntity<ErroResponse> handleSessaoAindaAberta(SessaoAindaAbertaException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(PautaComSessaoAbertaException.class)
    public ResponseEntity<ErroResponse> handlePautaComSessaoAberta(PautaComSessaoAbertaException ex){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<ErroResponse> handleCpfInvalido(CpfInvalidoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErroResponse(ex.getMessage()));
    }

    @ExceptionHandler(AssociadoNaoPodeVotarException.class)
    public ResponseEntity<ErroResponse> handleAssociadoNaoPodeVotar(AssociadoNaoPodeVotarException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErroResponse(ex.getMessage()));
    }
}
