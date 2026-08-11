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
        ErroResponse erro = new ErroResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(SessaoFechadaException.class)
    public ResponseEntity<ErroResponse> handleSessaoFechada(SessaoFechadaException ex){
        ErroResponse erro = new ErroResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(VotoDuplicadoException.class)
    public ResponseEntity<ErroResponse> handleVotoDuplicado(VotoDuplicadoException ex){
        ErroResponse erro = new ErroResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().getFirst().getDefaultMessage();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mensagem);
    }

    @ExceptionHandler(SessaoAindaAbertaException.class)
    public ResponseEntity<ErroResponse> handleSessaoAindaAberta(SessaoAindaAbertaException ex){
        ErroResponse erro = new ErroResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(PautaComSessaoAbertaException.class)
    public ResponseEntity<ErroResponse> handlePautaComSessaoAberta(PautaComSessaoAbertaException ex){
        ErroResponse erro = new ErroResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(CpfInvalidoException.class)
    public ResponseEntity<ErroResponse> handleCpfInvalido(CpfInvalidoException ex){
        ErroResponse erro = new ErroResponse(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(AssociadoNaoPodeVotarException.class)
    public ResponseEntity<ErroResponse> handleAssociadoNaoPodeVotar(AssociadoNaoPodeVotarException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErroResponse(ex.getMessage()));
    }
}
