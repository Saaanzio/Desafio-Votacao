package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.exception.RecursoNaoEncontradoException;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.repository.PautaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private PautaService pautaService;

    @Test
    void deveCriarPautaComSucesso(){
        Pauta pautaBanco = new Pauta("Pauta Teste", "Descrição Teste");

        when(pautaRepository.save(any())).thenReturn(pautaBanco);

        Pauta resultado = pautaService.criarPauta("Pauta Teste", "Descrição Teste");

        assertEquals("Pauta Teste", resultado.getTitulo());
        assertEquals("Descrição Teste", resultado.getDescricao());
    }

    @Test
    void deveBuscarPautaPorIdComSucesso(){
        Pauta pautaBanco = new Pauta("Pauta Teste", "Descrição Teste");

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pautaBanco));

        Pauta resultado = pautaService.buscarPautaPorId(1L);

        assertEquals("Pauta Teste", resultado.getTitulo());
        assertEquals("Descrição Teste", resultado.getDescricao());

    }

    @Test
    void deveLancarExcecaoQuandoPautaNaoExiste(){
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RecursoNaoEncontradoException.class, () -> pautaService.buscarPautaPorId(1L));
    }
}
