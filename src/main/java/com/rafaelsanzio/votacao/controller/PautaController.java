package com.rafaelsanzio.votacao.controller;

import com.rafaelsanzio.votacao.dto.request.PautaRequest;
import com.rafaelsanzio.votacao.dto.response.PautaResponse;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.service.PautaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pautas")
@Tag(name = "Pautas", description = "Cadastro e consulta de Pautas")
public class PautaController {

    private final PautaService pautaService;

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Pauta criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados da pauta inválidos.")
    })
    @Operation(summary = "Cadastrar uma nova pauta")
    public ResponseEntity<PautaResponse> criarPauta(@RequestBody @Valid PautaRequest pautaRequest){
        Pauta pauta = pautaService.criarPauta(pautaRequest.titulo(), pautaRequest.descricao());
        return ResponseEntity.status(HttpStatus.CREATED).body(PautaResponse.fromPautaToDto(pauta));
    }

    @GetMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pauta encontrada."),
            @ApiResponse(responseCode = "404", description = "Pauta não encontrada.")
    })
    @Operation(summary = "Buscar pauta por id")
    public ResponseEntity<PautaResponse> buscarPautaPorId(@PathVariable Long id){
        Pauta pauta = pautaService.buscarPautaPorId(id);
        return ResponseEntity.ok(PautaResponse.fromPautaToDto(pauta));
    }

    @GetMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pautas encontradas com sucesso."),
    })
    @Operation(summary = "Buscar todas as pautas")
    public ResponseEntity<List<PautaResponse>> buscarTodasPautas(){
        return ResponseEntity.ok(pautaService.buscarTodasPautas().stream().map(PautaResponse::fromPautaToDto).toList());
    }




}
