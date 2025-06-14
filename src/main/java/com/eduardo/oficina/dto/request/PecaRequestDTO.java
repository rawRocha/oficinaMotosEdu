package com.eduardo.oficina.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PecaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Marca é obrigatório")
    private String marca;

    @NotBlank(message = "Modelo da moto é obrigatório")
    private String modeloVeiculo;

    @Min(value = 1, message = "Quantidade deve ser no minimo 1")
    private int quantidade;

    @NotNull(message = "Preço de custo é obrigatório")
    @Digits(integer = 10, fraction = 2, message = "Preço de custo inválido")
    private BigDecimal precoCusto;

    @NotNull(message = "Preço de custo é obrigatório")
    @Digits(integer = 10, fraction = 2, message = "Preço de custo inválido")
    private BigDecimal precoVenda;

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
