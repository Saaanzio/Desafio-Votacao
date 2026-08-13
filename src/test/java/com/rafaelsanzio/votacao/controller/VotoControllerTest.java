package com.rafaelsanzio.votacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelsanzio.votacao.dto.request.VotoRequest;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.model.Voto;
import com.rafaelsanzio.votacao.model.enums.OpcaoVoto;
import com.rafaelsanzio.votacao.service.VotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(VotoController.class)
public class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VotoService votoService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveRegistrarVotoComSucesso() throws Exception{
        Pauta pautaBanco = new Pauta("Pauta teste", "Descrição teste");
        pautaBanco.setId(1L);

        Sessao sessaoBanco = new Sessao(
                pautaBanco,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5)
        );
        sessaoBanco.setId(1L);

        Voto votoBanco = new Voto(sessaoBanco, "12039123012", OpcaoVoto.SIM, LocalDateTime.now());
        votoBanco.setId(1L);

        when(votoService.registrarVoto(anyLong(), anyString(), any(OpcaoVoto.class))).thenReturn(votoBanco);

        VotoRequest resposta = new VotoRequest(1L, "12039123012", OpcaoVoto.SIM);

        mockMvc.perform(post("/votos").
                        contentType(MediaType.APPLICATION_JSON).
                        content(objectMapper.writeValueAsString(resposta)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.voto").value("SIM"));

    }
}
