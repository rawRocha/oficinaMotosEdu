package com.eduardo.oficina.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.eduardo.oficina.dto.request.MotoRequestDTO;
import com.eduardo.oficina.dto.response.MotoResponseDTO;
import com.eduardo.oficina.service.MotoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/motos")
public class MotoController {

    @Autowired
    private MotoService motoService;

    @PostMapping("/register")
    public ResponseEntity<MotoResponseDTO> cadastrarMoto(@RequestBody @Valid MotoRequestDTO dto) {
        MotoResponseDTO novaMoto = motoService.register(dto);
        return ResponseEntity.status(201).body(novaMoto);
    }

    @GetMapping
    public ResponseEntity<List<MotoResponseDTO>> listarMotos() {
        List<MotoResponseDTO> motos = motoService.findAll();
        return ResponseEntity.ok(motos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MotoResponseDTO> atualizarMoto(@PathVariable Long id,
            @RequestBody @Valid MotoRequestDTO dto) {
        MotoResponseDTO motoAtualizada = motoService.update(id, dto);
        return ResponseEntity.ok(motoAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMoto(@PathVariable Long id) {
        motoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
