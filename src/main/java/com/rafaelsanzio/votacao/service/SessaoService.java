package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.exception.RecursoNaoEncontradoException;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private static final int DURACAO_PADRAO_MINUTOS = 1;

    private final SessaoRepository sessaoRepository;

    private final PautaService pautaService;


    public Sessao abrirSessao(Long pautaId, Integer duracaoMinutos){
        Pauta pauta = pautaService.buscarPautaPorId(pautaId);

        int duracao = calcularDuracao(duracaoMinutos);
        LocalDateTime agora = LocalDateTime.now();
        Sessao sessao = new Sessao(pauta, agora, agora.plusMinutes(duracao));

        return sessaoRepository.save(sessao);
    }

    public Sessao buscarSessaoPorId(Long id){
        return sessaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Sessão não encontrada com o id " + id));
    }

    private int calcularDuracao(Integer duracaoMinutos){
        if(duracaoMinutos == null || duracaoMinutos <= 0){
            return DURACAO_PADRAO_MINUTOS;
        }
        return duracaoMinutos;
    }

    public boolean estaEncerrada(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);
        return LocalDateTime.now().isBefore(sessao.getDataFechamento());
    }
}
