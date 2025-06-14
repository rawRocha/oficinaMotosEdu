package com.eduardo.oficina.service;

import com.eduardo.oficina.dto.request.ItemPecaRequestDTO;
import com.eduardo.oficina.dto.request.OrdemServicoRequestDTO;
import com.eduardo.oficina.dto.request.ServicoRequestDTO;
import com.eduardo.oficina.dto.response.ClienteResponseDTO;
import com.eduardo.oficina.dto.response.OrdemServicoResponseDTO;
import com.eduardo.oficina.enums.StatusOrdemServico;
import com.eduardo.oficina.exception.EstoqueInsuficienteException;
import com.eduardo.oficina.model.*;
import com.eduardo.oficina.repository.*;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private PecaRepository pecaRepository;

    @Transactional
    public OrdemServico criarOrdemServico(OrdemServicoRequestDTO dto) {

        Moto moto = motoRepository.findById(dto.getMotoId())
                .orElseThrow(() -> new NoSuchElementException("Moto não encontrada"));

        OrdemServico ordem = new OrdemServico();
        ordem.setMoto(moto);
        ordem.setDescricaoProblema(dto.getDescricaoProblema());
        ordem.setObservacoes(dto.getObservacoes());
        ordem.setStatus(StatusOrdemServico.ABERTA);

        List<ServicoRealizado> servicos = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for (ServicoRequestDTO s : dto.getServicos()) {
            ServicoRealizado servico = new ServicoRealizado();
            servico.setDescricao(s.getDescricao());
            servico.setPreco(s.getPreco());
            servico.setOrdemServico(ordem);
            servicos.add(servico);
            valorTotal = valorTotal.add(s.getPreco());
        }

        ordem.setServicos(servicos);

        List<ItemPeca> itensPeca = new ArrayList<>();
        for (ItemPecaRequestDTO p : dto.getPecas()) {
            Peca peca = pecaRepository.findById(p.getPecaId())
                    .orElseThrow(() -> new NoSuchElementException("Peça não encontrada"));

            if (peca.getQuantidade() < p.getQuantidade()) {
                throw new EstoqueInsuficienteException(
                        "Estoque insuficiente para peça: " + peca.getNome() + " " + peca.getMarca() + " "
                                + peca.getModeloVeiculo());
            }

            // Atualiza estoque
            peca.setQuantidade(peca.getQuantidade() - p.getQuantidade());
            pecaRepository.save(peca); // salvar alteração do estoque no banco

            // Usa o preço do banco, não do DTO
            BigDecimal precoUnitario = peca.getPrecoVenda();

            ItemPeca item = new ItemPeca();
            item.setPeca(peca);
            item.setQuantidade(p.getQuantidade());
            item.setPrecoUnitario(precoUnitario);
            item.setOrdemServico(ordem);
            itensPeca.add(item);

            valorTotal = valorTotal.add(precoUnitario.multiply(BigDecimal.valueOf(p.getQuantidade())));
        }

        ordem.setPecas(itensPeca);
        ordem.setValorTotal(valorTotal);

        return ordemServicoRepository.save(ordem);
    }

    public List<OrdemServicoResponseDTO> listarOrdensServico(OrdemServicoRepository ordemServicoRepository) {
        List<OrdemServicoResponseDTO> all = ordemServicoRepository.findAll().stream()
                .map(ordem -> {
                    OrdemServicoResponseDTO dto = new OrdemServicoResponseDTO(ordem);

                    Cliente cliente = ordem.getMoto().getCliente();
                    ClienteResponseDTO clienteDTO = new ClienteResponseDTO(cliente);
                    dto.setCliente(clienteDTO);

                    return dto;
                })
                .collect(Collectors.toList());

        if (all.isEmpty()) {
            throw new NoSuchElementException("Lista vazia.");
        }

        return all;
    }

    @Transactional
    public void cancelarOrdemServico(Long idOrdem) {
        OrdemServico ordem = ordemServicoRepository.findById(idOrdem)
                .orElseThrow(() -> new NoSuchElementException("Ordem de serviço não encontrada."));

        for (ItemPeca item : ordem.getPecas()) {
            Peca peca = item.getPeca();
            peca.setQuantidade(peca.getQuantidade() + item.getQuantidade());
            pecaRepository.save(peca); // Atualiza o estoque no banco
        }

        ordemServicoRepository.deleteById(idOrdem);
    }

    @Transactional
    public OrdemServico atualizarOrdemServico(Long id, OrdemServicoRequestDTO dto) {
        OrdemServico ordem = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ordem de serviço não encontrada."));

        // Devolve peças antigas ao estoque
        for (ItemPeca item : ordem.getPecas()) {
            Peca peca = item.getPeca();
            peca.setQuantidade(peca.getQuantidade() + item.getQuantidade());
            pecaRepository.save(peca);
        }

        // Limpa listas antigas (manter o mesmo objeto de lista!)
        ordem.getPecas().clear();
        ordem.getServicos().clear();

        // Atualiza dados gerais
        ordem.setDescricaoProblema(dto.getDescricaoProblema());
        ordem.setObservacoes(dto.getObservacoes());

        BigDecimal valorTotal = BigDecimal.ZERO;

        // Adiciona novos serviços direto na lista
        for (ServicoRequestDTO s : dto.getServicos()) {
            ServicoRealizado servico = new ServicoRealizado();
            servico.setDescricao(s.getDescricao());
            servico.setPreco(s.getPreco());
            servico.setOrdemServico(ordem);
            ordem.getServicos().add(servico); // adiciona direto na lista existente
            valorTotal = valorTotal.add(s.getPreco());
        }

        // Adiciona novas peças e atualiza estoque
        for (ItemPecaRequestDTO p : dto.getPecas()) {
            Peca peca = pecaRepository.findById(p.getPecaId())
                    .orElseThrow(() -> new NoSuchElementException("Peça não encontrada."));

            if (peca.getQuantidade() < p.getQuantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente para peça: " + peca.getNome());
            }

            peca.setQuantidade(peca.getQuantidade() - p.getQuantidade());
            pecaRepository.save(peca);

            ItemPeca item = new ItemPeca();
            item.setPeca(peca);
            item.setQuantidade(p.getQuantidade());
            item.setPrecoUnitario(peca.getPrecoVenda());
            item.setOrdemServico(ordem);
            ordem.getPecas().add(item); // adiciona direto na lista existente

            valorTotal = valorTotal.add(peca.getPrecoVenda().multiply(BigDecimal.valueOf(p.getQuantidade())));
        }

        ordem.setValorTotal(valorTotal);

        return ordemServicoRepository.save(ordem);
    }

    @Transactional
    public void atualizarStatus(Long id, String status) {
        OrdemServico ordem = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ordem de serviço não encontrada."));

        try {
            StatusOrdemServico novoStatus = StatusOrdemServico.valueOf(status.toUpperCase());
            ordem.setStatus(novoStatus);
            ordemServicoRepository.save(ordem);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }

}
