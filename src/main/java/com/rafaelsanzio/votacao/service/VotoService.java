package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.dto.response.ResultadoVotacaoResponse;
import com.rafaelsanzio.votacao.exception.SessaoAindaAbertaException;
import com.rafaelsanzio.votacao.exception.SessaoFechadaException;
import com.rafaelsanzio.votacao.exception.VotoDuplicadoException;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.model.Voto;
import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import com.rafaelsanzio.votacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;

    private final SessaoService sessaoService;

    public Voto registrarVoto(Long sessaoId, Long associadoId, OpcaoVoto opcaoVoto){
        Sessao sessao = sessaoService.buscarSessaoPorId(sessaoId);
        LocalDateTime agora = LocalDateTime.now();

        if(agora.isAfter(sessao.getDataFechamento())){
            throw new SessaoFechadaException("Sessão " + sessaoId + " está fechada.");
        }

        boolean jaVotou = votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, associadoId);
        if(jaVotou){
            throw new VotoDuplicadoException("Associado de Id "+ associadoId +" já votou nesta sessão.");
        }
        return votoRepository.save(new Voto(sessao, associadoId, opcaoVoto, agora));
    }

    public ResultadoVotacaoResponse buscarResultados(Long sessaoId){
        if(sessaoService.estaEncerrada(sessaoId)){
            throw new SessaoAindaAbertaException("Impossível buscar resultados de votos em sessões abertas.");
        }
        long votosSim = votoRepository.countBySessaoIdAndOpcao(sessaoId, OpcaoVoto.SIM);
        long votosNao = votoRepository.countBySessaoIdAndOpcao(sessaoId, OpcaoVoto.NAO);
        return new ResultadoVotacaoResponse(sessaoId, votosSim, votosNao);
    }

}
