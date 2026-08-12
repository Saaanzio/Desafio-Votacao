package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.exception.PautaComSessaoAbertaException;
import com.rafaelsanzio.votacao.exception.RecursoNaoEncontradoException;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.repository.SessaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessaoService {

    private static final int DURACAO_PADRAO_MINUTOS = 1;

    private final SessaoRepository sessaoRepository;

    private final PautaService pautaService;


    public Sessao abrirSessao(Long pautaId, Integer duracaoMinutos){
        Pauta pauta = pautaService.buscarPautaPorId(pautaId);
        LocalDateTime agora = LocalDateTime.now();
        if(sessaoRepository.existsByPautaIdAndDataFechamentoAfter(pautaId, agora)){
            throw new PautaComSessaoAbertaException("Já existe uma sessão aberta para esta pauta. Espere seu termino e tente novamente mais tarde.");
        }
        int duracao = calcularDuracao(duracaoMinutos);
        Sessao sessao = new Sessao(pauta, agora, agora.plusMinutes(duracao));

        return sessaoRepository.save(sessao);
    }

    public Sessao buscarSessaoPorId(Long id){
        return sessaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Sessão não encontrada com o id " + id));
    }

    public List<Sessao> buscarTodasSessoes(){
        return sessaoRepository.findAll();
    }

    private int calcularDuracao(Integer duracaoMinutos){
        if(duracaoMinutos == null || duracaoMinutos <= 0){
            return DURACAO_PADRAO_MINUTOS;
        }
        return duracaoMinutos;
    }

    public boolean estaAberta(Long sessaoId) {
        Sessao sessao = buscarSessaoPorId(sessaoId);
        return LocalDateTime.now().isBefore(sessao.getDataFechamento());
    }
}
