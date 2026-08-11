package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.dto.response.ResultadoSessaoResponse;
import com.rafaelsanzio.votacao.exception.SessaoFechadaException;
import com.rafaelsanzio.votacao.exception.VotoDuplicadoException;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.model.Voto;
import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import com.rafaelsanzio.votacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public ResultadoSessaoResponse buscarResultados(Long sessaoId){
        long votosSim = votoRepository.countBySessaoIdAndVoto(sessaoId, OpcaoVoto.SIM);
        long votosNao = votoRepository.countBySessaoIdAndVoto(sessaoId, OpcaoVoto.NAO);
        return new ResultadoSessaoResponse(sessaoId, votosSim, votosNao);



    }

}
