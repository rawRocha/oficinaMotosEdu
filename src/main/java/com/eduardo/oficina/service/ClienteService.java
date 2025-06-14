package com.eduardo.oficina.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.oficina.dto.request.ClienteRequestDTO;
import com.eduardo.oficina.dto.response.ClienteResponseDTO;
import com.eduardo.oficina.model.Cliente;
import com.eduardo.oficina.repository.ClienteRepository;
import com.eduardo.oficina.repository.OrdemServicoRepository;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    public ClienteResponseDTO register(ClienteRequestDTO dto) {
        if (clienteRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new IllegalStateException("Cpf já cadastrado em um cliente.");
        }

        if (clienteRepository.findByContato(dto.getContato()).isPresent()) {
            throw new IllegalStateException("Contato já cadastrado em um cliente.");
        }

        Cliente novoCliente = new Cliente();
        novoCliente.setNome(dto.getNome());
        novoCliente.setCpf(dto.getCpf());
        novoCliente.setContato(dto.getContato());

        Cliente saveCliente = clienteRepository.save(novoCliente);

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setId(saveCliente.getId());
        response.setNome(saveCliente.getNome());
        response.setCpf(saveCliente.getCpf());
        response.setContato(saveCliente.getContato());

        return response;
    }

    public List<ClienteResponseDTO> listAll() {
        List<ClienteResponseDTO> all = clienteRepository.findAll()
                .stream()
                .map(ClienteResponseDTO::new)
                .collect(Collectors.toList());

        if (all.isEmpty()) {
            throw new NoSuchElementException("Lista vazia.");
        }

        return all;
    }

    public ClienteResponseDTO update(Long id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado."));

        cliente.setNome(dto.getNome());
        cliente.setCpf(dto.getCpf());
        cliente.setContato(dto.getContato());

        Cliente updatedCliente = clienteRepository.save(cliente);
        return new ClienteResponseDTO(updatedCliente);
    }

    public void delete(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado"));

        boolean temMotosComOrdens = cliente.getMotos().stream()
                .anyMatch(moto -> !ordemServicoRepository.findByMotoId(moto.getId()).isEmpty());

        if (temMotosComOrdens) {
            throw new IllegalStateException(
                    "Não é possível excluir este cliente pois há motos com ordens de serviço ativas.");
        }

        clienteRepository.delete(cliente);
    }
}
