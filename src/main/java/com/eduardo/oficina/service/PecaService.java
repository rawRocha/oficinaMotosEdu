package com.eduardo.oficina.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.oficina.dto.request.PecaRequestDTO;
import com.eduardo.oficina.dto.response.PecaResponseDTO;
import com.eduardo.oficina.model.Peca;
import com.eduardo.oficina.repository.PecaRepository;

@Service
public class PecaService {

    @Autowired
    private PecaRepository pecaRepository;

    public PecaResponseDTO register(PecaRequestDTO dto) {
        String nome = dto.getNome().trim().toUpperCase();
        String marca = dto.getMarca().trim().toUpperCase();
        String modeloVeiculo = dto.getModeloVeiculo().trim().toUpperCase();

        // Buscar peça existente (normalizando os dados)
        var pecaExistenteOpt = pecaRepository.findByNomeAndMarcaAndModeloVeiculo(nome, marca, modeloVeiculo);

        Peca pecaSalva;

        if (pecaExistenteOpt.isPresent()) {
            // Incrementa a quantidade da peça existente
            Peca pecaExistente = pecaExistenteOpt.get();
            pecaExistente.setQuantidade(pecaExistente.getQuantidade() + dto.getQuantidade());

            // Opcional: atualizar preços se desejar
            // pecaExistente.setPrecoCusto(dto.getPrecoCusto());
            // pecaExistente.setPrecoVenda(dto.getPrecoVenda());

            pecaSalva = pecaRepository.save(pecaExistente);
        } else {
            // Criar nova peça
            Peca novaPeca = new Peca();
            novaPeca.setNome(nome);
            novaPeca.setMarca(marca);
            novaPeca.setModeloVeiculo(modeloVeiculo);
            novaPeca.setQuantidade(dto.getQuantidade());
            novaPeca.setPrecoCusto(dto.getPrecoCusto());
            novaPeca.setPrecoVenda(dto.getPrecoVenda());

            pecaSalva = pecaRepository.save(novaPeca);
        }

        // Construir DTO de resposta
        PecaResponseDTO response = new PecaResponseDTO();
        response.setId(pecaSalva.getId());
        response.setNome(pecaSalva.getNome());
        response.setMarca(pecaSalva.getMarca());
        response.setModeloVeiculo(pecaSalva.getModeloVeiculo());
        response.setQuantidade(pecaSalva.getQuantidade());
        response.setPrecoCusto(pecaSalva.getPrecoCusto());
        response.setPrecoVenda(pecaSalva.getPrecoVenda());

        return response;
    }

    public List<PecaResponseDTO> buscarPorNome(String nome) {
        List<Peca> pecas = pecaRepository.findByNomeContainingIgnoreCase(nome.trim().toUpperCase());
        return pecas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Listar todas as peças
    public List<PecaResponseDTO> listAll() {
        return pecaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Atualizar peça existente
    public PecaResponseDTO update(Long id, PecaRequestDTO dto) {
        Peca peca = pecaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Peça não encontrada com ID: " + id));

        peca.setNome(dto.getNome().trim().toUpperCase());
        peca.setMarca(dto.getMarca().trim().toUpperCase());
        peca.setModeloVeiculo(dto.getModeloVeiculo().trim().toUpperCase());
        peca.setQuantidade(dto.getQuantidade());
        peca.setPrecoCusto(dto.getPrecoCusto());
        peca.setPrecoVenda(dto.getPrecoVenda());

        Peca atualizada = pecaRepository.save(peca);

        return toDTO(atualizada);
    }

    // Deletar peça por ID
    public void delete(Long id) {
        Peca peca = pecaRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Peça não encontrada"));

        if (!peca.getItens().isEmpty()) {
            throw new IllegalStateException("Peça não pode ser deletada pois está vinculada a uma ordem de serviço.");
        }

        pecaRepository.deleteById(id);
    }

    // Conversão para DTO de resposta
    private PecaResponseDTO toDTO(Peca peca) {
        PecaResponseDTO dto = new PecaResponseDTO();
        dto.setId(peca.getId());
        dto.setNome(peca.getNome());
        dto.setMarca(peca.getMarca());
        dto.setModeloVeiculo(peca.getModeloVeiculo());
        dto.setQuantidade(peca.getQuantidade());
        dto.setPrecoCusto(peca.getPrecoCusto());
        dto.setPrecoVenda(peca.getPrecoVenda());
        return dto;
    }
}
