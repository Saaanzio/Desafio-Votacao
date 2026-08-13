package com.rafaelsanzio.votacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelsanzio.votacao.dto.request.SessaoRequest;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.service.SessaoService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(SessaoController.class)
public class SessaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessaoService sessaoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveAbrirSessaoComSucesso() throws Exception{
        Pauta pautaBanco = new Pauta("Pauta teste", "Descrição teste");
        pautaBanco.setId(1L);

        Sessao sessaoBanco = new Sessao(
                pautaBanco,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5)
        );
        sessaoBanco.setId(1L);

        SessaoRequest resposta = new SessaoRequest(1L, 5);

        when(sessaoService.abrirSessao(anyLong(), any())).thenReturn(sessaoBanco);

        mockMvc.perform(post("/sessoes").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(resposta)))
                .andExpect(jsonPath("$.id").value(1));
    }
}
