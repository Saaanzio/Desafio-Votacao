package com.rafaelsanzio.votacao.service;

import com.rafaelsanzio.votacao.exception.RecursoNaoEncontradoException;
import com.rafaelsanzio.votacao.model.Pauta;
import com.rafaelsanzio.votacao.repository.PautaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;

    public Pauta criarPauta(String titulo, String descricao){
        return pautaRepository.save(new Pauta(titulo, descricao));
    }

    public Pauta buscarPautaPorId(Long id){
        return pautaRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Pauta não encontrada com o id " + id));
    }
}
