package com.rafaelsanzio.votacao.client;

import com.rafaelsanzio.votacao.exception.CpfInvalidoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class CpfClienteFake implements CpfClient{

    private final Random aleatorio = new Random();
    private static final int PROBABILIDADE_CPF_INVALIDO = 10;
    private static final int PROBABILIDADE_NAO_PODE_VOTAR = 10;
    @Value("${cpf.validacao.desativada:false}")
    private boolean validacaoDesativada;


    @Override
    public CpfStatus cpfValido(String cpf) {
        if(validacaoDesativada){
            return CpfStatus.ABLE_TO_VOTE;
        }
        int resultado = aleatorio.nextInt(100);
        if (resultado < PROBABILIDADE_CPF_INVALIDO){
             throw new CpfInvalidoException("Cpf Inválido " + cpf);
         }
        else if(resultado < PROBABILIDADE_NAO_PODE_VOTAR + PROBABILIDADE_CPF_INVALIDO){
             return CpfStatus.UNABLE_TO_VOTE;
         }
        else{
            return CpfStatus.ABLE_TO_VOTE;
        }
    }
}
