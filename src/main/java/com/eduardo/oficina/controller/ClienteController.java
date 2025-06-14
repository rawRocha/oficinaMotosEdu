package com.eduardo.oficina.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eduardo.oficina.dto.request.ClienteRequestDTO;
import com.eduardo.oficina.dto.response.ClienteResponseDTO;
import com.eduardo.oficina.service.ClienteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping("/register")
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO dto) {
        ClienteResponseDTO novoCliente = clienteService.register(dto);

        return ResponseEntity.status(201).body(novoCliente);
    }

    // GET: Listar todos os clientes
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listAll();
        return ResponseEntity.ok(clientes);
    }

    // PUT: Atualizar um cliente por ID
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(
            @PathVariable Long id,
            @RequestBody @Valid ClienteRequestDTO dto) {
        ClienteResponseDTO clienteAtualizado = clienteService.update(id, dto);
        return ResponseEntity.ok(clienteAtualizado);
    }

    // DELETE: Remover um cliente por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
