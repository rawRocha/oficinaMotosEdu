package com.eduardo.oficina.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.eduardo.oficina.dto.request.PecaRequestDTO;
import com.eduardo.oficina.dto.response.PecaResponseDTO;
import com.eduardo.oficina.model.Peca;
import com.eduardo.oficina.repository.PecaRepository;
import com.eduardo.oficina.service.PecaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/pecas")
@Validated
public class PecaController {

    @Autowired
    private PecaService pecaService;

    @Autowired
    private PecaRepository pecaRepository;

    @PostMapping("/register")
    public ResponseEntity<PecaResponseDTO> cadastrarPeca(@Valid @RequestBody PecaRequestDTO dto) {
        PecaResponseDTO response = pecaService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PecaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(pecaService.listAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PecaResponseDTO> atualizarPeca(
            @PathVariable Long id,
            @RequestBody @Valid PecaRequestDTO dto) {
        return ResponseEntity.ok(pecaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPeca(@PathVariable Long id) {
        pecaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPorNome")
    public ResponseEntity<List<PecaResponseDTO>> buscarPorNome(@RequestParam String nome) {
        try {
            List<PecaResponseDTO> pecas = pecaService.buscarPorNome(nome);
            return ResponseEntity.ok(pecas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Peca> buscarPorId(@PathVariable Long id) {
        Optional<Peca> peca = pecaRepository.findById(id);
        return peca.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
