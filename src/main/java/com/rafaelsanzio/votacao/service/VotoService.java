package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.client.CpfClient;
import com.rafaelsanzio.votacao.client.CpfStatus;
import com.rafaelsanzio.votacao.dto.response.ResultadoVotacaoResponse;
import com.rafaelsanzio.votacao.exception.AssociadoNaoPodeVotarException;
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

    private final CpfClient cpfClient;

    public Voto registrarVoto(Long sessaoId, String associadoId, OpcaoVoto opcaoVoto){
        Sessao sessao = sessaoService.buscarSessaoPorId(sessaoId);
        LocalDateTime agora = LocalDateTime.now();

        if(agora.isAfter(sessao.getDataFechamento())){
            throw new SessaoFechadaException("Sessão " + sessaoId + " está fechada.");
        }

        if(cpfClient.cpfValido(associadoId) == CpfStatus.UNABLE_TO_VOTE){
            throw new AssociadoNaoPodeVotarException("O associado não pode votar");
        }
        boolean jaVotou = votoRepository.existsBySessaoPautaIdAndAssociadoId(sessao.getPauta().getId(), associadoId);
        if(jaVotou){
            throw new VotoDuplicadoException("Associado de Id "+ associadoId +" já votou nesta sessão.");
        }
        return votoRepository.save(new Voto(sessao, associadoId, opcaoVoto, agora));
    }

    public ResultadoVotacaoResponse buscarResultados(Long sessaoId){
        if(sessaoService.estaAberta(sessaoId)){
            throw new SessaoAindaAbertaException("Impossível buscar resultados de votos em sessões abertas.");
        }
        long votosSim = votoRepository.countBySessaoIdAndOpcao(sessaoId, OpcaoVoto.SIM);
        long votosNao = votoRepository.countBySessaoIdAndOpcao(sessaoId, OpcaoVoto.NAO);
        return new ResultadoVotacaoResponse(sessaoId, votosSim, votosNao);
    }

}
