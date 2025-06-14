package com.eduardo.oficina.dto.response;

import java.math.BigDecimal;

import com.eduardo.oficina.model.ServicoRealizado;

public class ServicoResponseDTO {

    private Long id;
    private String descricao;
    private BigDecimal preco;

    public ServicoResponseDTO() {

    }

    // ✅ Construtor que recebe a entidade ServicoRealizado
    public ServicoResponseDTO(ServicoRealizado servico) {
        this.id = servico.getId();
        this.descricao = servico.getDescricao();
        this.preco = servico.getPreco();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

}
