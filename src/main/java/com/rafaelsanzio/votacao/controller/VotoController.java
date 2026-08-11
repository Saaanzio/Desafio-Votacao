package com.rafaelsanzio.votacao.controller;

import com.rafaelsanzio.votacao.dto.request.VotoRequest;
import com.rafaelsanzio.votacao.dto.response.ResultadoVotacaoResponse;
import com.rafaelsanzio.votacao.dto.response.VotoResponse;
import com.rafaelsanzio.votacao.model.Voto;
import com.rafaelsanzio.votacao.service.VotoService;
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
@RequestMapping("/votos")
@Tag(name = "Votos", description = "Cadastro e consulta de votos")
public class VotoController {

    private final VotoService votoService;

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Voto registrado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados do voto inválidos.")
    })
    @Operation(summary = "Registrar um voto")
    public ResponseEntity<VotoResponse> registrarVoto(@RequestBody @Valid VotoRequest votoRequest){
        Voto voto = votoService.registrarVoto(votoRequest.sessaoId(), votoRequest.associadoId(), votoRequest.voto());
        return ResponseEntity.status(HttpStatus.CREATED).body(VotoResponse.fromVotoToDto(voto));
    }

    @GetMapping("/resultado/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Resultados encontrados com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de sessão inválidos."),
            @ApiResponse(responseCode = "400", description = "Sessão em andamento.")
    })
    @Operation(summary = "Buscar resultados dos votos por Id de sessão")
    public ResponseEntity<ResultadoVotacaoResponse> buscarResultadoDosVotosPorSessaoId(@PathVariable Long sessaoId){
        ResultadoVotacaoResponse resultadoVotacaoResponse = votoService.buscarResultados(sessaoId);
        return ResponseEntity.ok(resultadoVotacaoResponse);
    }

}
