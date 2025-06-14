package com.eduardo.oficina.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eduardo.oficina.dto.request.MotoRequestDTO;
import com.eduardo.oficina.dto.response.MotoResponseDTO;
import com.eduardo.oficina.model.Cliente;
import com.eduardo.oficina.model.Moto;
import com.eduardo.oficina.repository.ClienteRepository;
import com.eduardo.oficina.repository.MotoRepository;

@Service
public class MotoService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MotoRepository motoRepository;

    public MotoResponseDTO register(MotoRequestDTO dto) {
        if (motoRepository.findByPlaca(dto.getPlaca()).isPresent()) {
            throw new IllegalStateException("Placa já pertence a uma moto cadastrada.");
        }

        Moto novaMoto = new Moto();
        novaMoto.setMarca(dto.getMarca());
        novaMoto.setModelo(dto.getModelo());
        novaMoto.setAno(dto.getAno());
        novaMoto.setPlaca(dto.getPlaca());

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado."));

        novaMoto.setCliente(cliente);

        // Aqui você salva a moto
        novaMoto = motoRepository.save(novaMoto);

        // Agora monta a resposta
        MotoResponseDTO response = new MotoResponseDTO();
        response.setId(novaMoto.getId());
        response.setMarca(novaMoto.getMarca());
        response.setModelo(novaMoto.getModelo());
        response.setPlaca(novaMoto.getPlaca());
        response.setAno(novaMoto.getAno());
        response.setIdCliente(cliente.getId());

        return response;
    }

    public List<MotoResponseDTO> findAll() {
        List<Moto> motos = motoRepository.findAll();
        return motos.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MotoResponseDTO update(Long id, MotoRequestDTO dto) {
        Moto motoExistente = motoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Moto não encontrada para atualização."));

        // Verificar se a placa está sendo alterada para uma já existente (exceto para a
        // própria moto)
        Optional<Moto> motoComPlaca = motoRepository.findByPlaca(dto.getPlaca());
        if (motoComPlaca.isPresent() && !motoComPlaca.get().getId().equals(id)) {
            throw new IllegalStateException("Placa já pertence a outra moto cadastrada.");
        }

        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new NoSuchElementException("Cliente não encontrado."));

        motoExistente.setMarca(dto.getMarca());
        motoExistente.setModelo(dto.getModelo());
        motoExistente.setAno(dto.getAno());
        motoExistente.setPlaca(dto.getPlaca());
        motoExistente.setCliente(cliente);

        Moto motoAtualizada = motoRepository.save(motoExistente);

        return toResponseDTO(motoAtualizada);
    }

    public void delete(Long id) {
        Moto moto = motoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Moto não encontrada para exclusão."));
        motoRepository.delete(moto);
    }

    private MotoResponseDTO toResponseDTO(Moto moto) {
        MotoResponseDTO response = new MotoResponseDTO();
        response.setId(moto.getId());
        response.setMarca(moto.getMarca());
        response.setModelo(moto.getModelo());
        response.setPlaca(moto.getPlaca());
        response.setAno(moto.getAno());
        response.setIdCliente(moto.getCliente().getId());
        return response;
    }
}
