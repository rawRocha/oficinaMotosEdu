package com.eduardo.oficina.dto.response;

import java.math.BigDecimal;

import com.eduardo.oficina.model.ItemPeca;

public class ItemPecaResponseDTO {

    private Long id;
    private String nomePeca;
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemPecaResponseDTO() {

    }

    // Construtor que mapeia entidade para DTO
    public ItemPecaResponseDTO(ItemPeca itemPeca) {
        this.id = itemPeca.getId();
        this.nomePeca = itemPeca.getPeca().getNome(); // ou itemPeca.getPeca().getNome(), depende do seu modelo
        this.quantidade = itemPeca.getQuantidade();
        this.precoUnitario = itemPeca.getPrecoUnitario();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePeca() {
        return nomePeca;
    }

    public void setNomePeca(String nomePeca) {
        this.nomePeca = nomePeca;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

}
