package com.eduardo.oficina.dto.request;

import java.util.List;

public class OrdemServicoRequestDTO {

    private Long motoId;
    private String descricaoProblema;
    private String observacoes;

    private List<ServicoRequestDTO> servicos;
    private List<ItemPecaRequestDTO> pecas;

    public Long getMotoId() {
        return motoId;
    }

    public void setMotoId(Long motoId) {
        this.motoId = motoId;
    }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public List<ServicoRequestDTO> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoRequestDTO> servicos) {
        this.servicos = servicos;
    }

    public List<ItemPecaRequestDTO> getPecas() {
        return pecas;
    }

    public void setPecas(List<ItemPecaRequestDTO> pecas) {
        this.pecas = pecas;
    }
}
