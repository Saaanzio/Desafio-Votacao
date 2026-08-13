package com.rafaelsanzio.votacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rafaelsanzio.votacao.dto.request.PautaRequest;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.service.PautaService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PautaController.class)
public class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PautaService pautaService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deveCriarPautaComSucesso() throws Exception{
        Pauta pautaBanco = new Pauta("Pauta teste", "Descrição teste");
        pautaBanco.setId(1L);

        when(pautaService.criarPauta(anyString(), anyString())).thenReturn(pautaBanco);

        PautaRequest resposta = new PautaRequest("Pauta teste", "Descrição teste");

        mockMvc.perform(post("/pautas").
                contentType(MediaType.APPLICATION_JSON).
                content(objectMapper.writeValueAsString(resposta)))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.titulo").value("Pauta teste"));
    }

}
