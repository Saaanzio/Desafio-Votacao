package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.client.CpfClient;
import com.rafaelsanzio.votacao.client.CpfStatus;
import com.rafaelsanzio.votacao.dto.response.ResultadoVotacaoResponse;
import com.rafaelsanzio.votacao.exception.SessaoFechadaException;
import com.rafaelsanzio.votacao.exception.VotoDuplicadoException;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.model.Voto;
import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import com.rafaelsanzio.votacao.repository.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;

    @Mock
    private SessaoService sessaoService;

    @Mock
    private CpfClient cpfClient;

    @InjectMocks
    private VotoService votoService;

    @Test
    void deveRegistrarVotoComSucesso(){
        Sessao sessaoTeste = sessaoAberta();
        Voto votoBanco = new Voto(sessaoTeste, "123", OpcaoVoto.SIM, LocalDateTime.now());

        when(sessaoService.buscarSessaoPorId(1L)).thenReturn(sessaoTeste);
        when(cpfClient.cpfValido("123")).thenReturn(CpfStatus.ABLE_TO_VOTE);
        when(votoRepository.existsByPautaIdAndAssociadoId(1L, "123")).thenReturn(false);
        when(votoRepository.save(any())).thenReturn(votoBanco);

        Voto resultado = votoService.registrarVoto(1L, "123", OpcaoVoto.SIM);

        assertEquals(OpcaoVoto.SIM, resultado.getOpcao());
    }

    @Test
    void naoDeveVotarEmSessaoFechada(){
        Pauta pautaBanco = new Pauta("Pauta Teste", "Descrição Teste");
        pautaBanco.setId(1L);
        Sessao sessao = new Sessao(
                pautaBanco,
                LocalDateTime.now().minusMinutes(2),
                LocalDateTime.now().minusMinutes(1)
        );

        when(sessaoService.buscarSessaoPorId(1L)).thenReturn(sessao);

        assertThrows( SessaoFechadaException.class, () -> votoService.registrarVoto(1L, "123", OpcaoVoto.SIM));
    }

    @Test
    void naoDevePermitirVotoDuplicado(){
        Sessao sessaoTeste = sessaoAberta();

        when(sessaoService.buscarSessaoPorId(1L)).thenReturn(sessaoTeste);
        when(cpfClient.cpfValido("123")).thenReturn(CpfStatus.ABLE_TO_VOTE);
        when(votoRepository.existsByPautaIdAndAssociadoId(1L, "123")).thenReturn(true);

        assertThrows( VotoDuplicadoException.class, () -> votoService.registrarVoto(1L, "123", OpcaoVoto.SIM));
    }

    @Test
    void deveContabilizarResultadosComSucesso(){
        when(sessaoService.estaAberta(1L)).thenReturn(false);
        when(votoRepository.countBySessaoIdAndOpcao(1L, OpcaoVoto.SIM))
                .thenReturn(7L);
        when(votoRepository.countBySessaoIdAndOpcao(1L, OpcaoVoto.NAO))
                .thenReturn(5L);

        ResultadoVotacaoResponse resultado = votoService.buscarResultados(1L);

        assertEquals(7L, resultado.votosSim());
        assertEquals(5L, resultado.votosNao());
    }

    private Sessao sessaoAberta() {
        Pauta pautaBanco = new Pauta("Pauta Teste", "Descrição Teste");
        pautaBanco.setId(1L);
        return new Sessao(
                pautaBanco,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10)
        );
    }
}
