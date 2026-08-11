package com.rafaelsanzio.votacao.controller;

import com.rafaelsanzio.votacao.dto.request.SessaoRequest;
import com.rafaelsanzio.votacao.dto.response.ResultadoVotacaoResponse;
import com.rafaelsanzio.votacao.dto.response.SessaoResponse;
import com.rafaelsanzio.votacao.model.Sessao;
import com.rafaelsanzio.votacao.service.SessaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sessoes")
@Tag(name = "Sessoes", description = "Cadastro e consulta de sessões.")
public class SessaoController {

    private final SessaoService sessaoService;

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Sessão aberta com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da sessão inválidos.")
    })
    @Operation(summary = "Abrir uma sessão")
    public ResponseEntity<SessaoResponse> abrirSessao(@RequestBody @Valid SessaoRequest sessaoRequest){
        Sessao sessao = sessaoService.abrirSessao(sessaoRequest.pautaId(), sessaoRequest.duracaoEmMinutos());
        return ResponseEntity.status(HttpStatus.CREATED).body(SessaoResponse.fromSessaoToDto(sessao));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sessão encontrada."),
            @ApiResponse(responseCode = "404", description = "Sessão não encontrada.")
    })
    @Operation(summary = "Buscar sessão por id")
    public ResponseEntity<SessaoResponse> buscarSessaoPorId(@PathVariable Long id){
        Sessao sessao = sessaoService.buscarSessaoPorId(id);
        return ResponseEntity.ok(SessaoResponse.fromSessaoToDto(sessao));
    }
}
