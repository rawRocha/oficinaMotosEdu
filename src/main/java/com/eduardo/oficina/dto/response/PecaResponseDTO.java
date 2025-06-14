package com.eduardo.oficina.dto.response;

import java.math.BigDecimal;

import com.eduardo.oficina.model.Peca;

public class PecaResponseDTO {
    private Long id;
    private String nome;
    private String marca;
    private String modeloVeiculo;
    private int quantidade;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;

    public PecaResponseDTO() {

    }

    // Construtor que mapeia entidade para DTO
    public PecaResponseDTO(Peca peca) {
        this.id = peca.getId();
        this.nome = peca.getNome();
        this.marca = peca.getMarca();
        this.modeloVeiculo = peca.getModeloVeiculo();
        this.quantidade = peca.getQuantidade();
        this.precoCusto = peca.getPrecoCusto();
        this.precoVenda = peca.getPrecoVenda();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModeloVeiculo() {
        return modeloVeiculo;
    }

    public void setModeloVeiculo(String modeloVeiculo) {
        this.modeloVeiculo = modeloVeiculo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }
}
