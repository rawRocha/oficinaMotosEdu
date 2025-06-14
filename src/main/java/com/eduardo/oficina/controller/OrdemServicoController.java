package com.eduardo.oficina.controller;

import com.eduardo.oficina.dto.request.AtualizarStatusRequestDTO;
import com.eduardo.oficina.dto.request.OrdemServicoRequestDTO;
import com.eduardo.oficina.dto.response.OrdemServicoResponseDTO;
import com.eduardo.oficina.mapper.OrdemServicoMapper;
import com.eduardo.oficina.model.OrdemServico;
import com.eduardo.oficina.repository.OrdemServicoRepository;
import com.eduardo.oficina.service.OrdemServicoService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    @Autowired
    private OrdemServicoService ordemServicoService;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @PostMapping("/nova-ordem-servico")
    public OrdemServicoResponseDTO criar(@RequestBody @Valid OrdemServicoRequestDTO dto) {
        OrdemServico ordem = ordemServicoService.criarOrdemServico(dto);
        return OrdemServicoMapper.toResponseDTO(ordem);
    }

    @GetMapping
    public ResponseEntity<List<OrdemServicoResponseDTO>> listar() {
        return ResponseEntity.ok(ordemServicoService.listarOrdensServico(ordemServicoRepository));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        ordemServicoService.cancelarOrdemServico(id);
        return ResponseEntity.ok("Ordem de serviço deletada com sucesso.");
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<String> atualizarOrdemServico(
            @PathVariable Long id,
            @RequestBody @Valid OrdemServicoRequestDTO dto) {

        ordemServicoService.atualizarOrdemServico(id, dto);
        return ResponseEntity.ok("Ordem de serviço atualizada com sucesso.");
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<String> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizarStatusRequestDTO dto) {

        ordemServicoService.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.ok("Status atualizado com sucesso.");
    }
}
