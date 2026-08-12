package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.exception.PautaComSessaoAbertaException;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.repository.SessaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository;

    @Mock
    private PautaService pautaService;

    @InjectMocks
    private SessaoService sessaoService;

    @Test
    void deveAbrirSessaoComDuracaoInformada(){
        Pauta pautaBanco = new Pauta("Pauta Teste", "Descrição Teste");
        Sessao sessaoBanco = new Sessao(
                pautaBanco,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10)
        );

        when(pautaService.buscarPautaPorId(1L)).thenReturn(pautaBanco);
        when(sessaoRepository.existsByPautaIdAndDataFechamentoAfter(eq(1L), any())).thenReturn(false);
        when(sessaoRepository.save(any())).thenReturn(sessaoBanco);

        Sessao resultado = sessaoService.abrirSessao(1L, 10);

        assertEquals(pautaBanco, resultado.getPauta());
    }

    @Test
    void deveUsarDuracaoPadraoQuandoNenhumValorInformado(){
        Pauta pautaBanco = new Pauta("Pauta Teste", "Descrição Teste");
        Sessao sessaoBanco = new Sessao(
                pautaBanco,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10)
        );

        when(pautaService.buscarPautaPorId(1L)).thenReturn(pautaBanco);
        when(sessaoRepository.existsByPautaIdAndDataFechamentoAfter(eq(1L), any())).thenReturn(false);
        when(sessaoRepository.save(any())).thenReturn(sessaoBanco);

        Sessao resultado = sessaoService.abrirSessao(1L, null);

        assertNotNull(resultado);

    }

    @Test
    void naoDeveAbrirSessaoCasoJaExistaUmaAberta(){
        Pauta pautaBanco = new Pauta("Pauta Teste", "Descrição Teste");
        when(pautaService.buscarPautaPorId(1L)).thenReturn(pautaBanco);
        when(sessaoRepository.existsByPautaIdAndDataFechamentoAfter(eq(1L), any())).thenReturn(true);

        assertThrows(PautaComSessaoAbertaException.class, () -> sessaoService.abrirSessao(1L, 10));

    }
}
