package com.eduardo.oficina.dto.response;

import com.eduardo.oficina.enums.StatusOrdemServico;
import com.eduardo.oficina.model.OrdemServico;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrdemServicoResponseDTO {

    private Long id;
    private Long motoId;
    private String placaMoto;
    private String descricaoProblema;
    private String observacoes;
    private StatusOrdemServico status;
    private BigDecimal valorTotal;
    private LocalDateTime dataAbertura;
    private LocalDateTime dataAtualizacao;

    private List<ServicoResponseDTO> servicos;
    private List<ItemPecaResponseDTO> pecas;

    private ClienteResponseDTO cliente;

    public ClienteResponseDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResponseDTO cliente) {
        this.cliente = cliente;
    }

    public OrdemServicoResponseDTO() {

    }

    // ✅ Construtor que recebe OrdemServico
    public OrdemServicoResponseDTO(OrdemServico ordemServico) {
        this.id = ordemServico.getId();
        this.motoId = ordemServico.getMoto().getId();
        this.placaMoto = ordemServico.getMoto().getPlaca(); // Certifique-se que Moto tem getPlaca()
        this.descricaoProblema = ordemServico.getDescricaoProblema();
        this.observacoes = ordemServico.getObservacoes();
        this.status = ordemServico.getStatus();
        this.valorTotal = ordemServico.getValorTotal();
        this.dataAbertura = ordemServico.getCreatedAt();
        this.dataAtualizacao = ordemServico.getUpdatedAt();

        this.servicos = ordemServico.getServicos()
                .stream()
                .map(ServicoResponseDTO::new)
                .collect(Collectors.toList());

        this.pecas = ordemServico.getPecas()
                .stream()
                .map(ItemPecaResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMotoId() {
        return motoId;
    }

    public void setMotoId(Long motoId) {
        this.motoId = motoId;
    }

    public String getPlacaMoto() {
        return placaMoto;
    }

    public void setPlacaMoto(String placaMoto) {
        this.placaMoto = placaMoto;
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

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDateTime getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(LocalDateTime dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public List<ServicoResponseDTO> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoResponseDTO> servicos) {
        this.servicos = servicos;
    }

    public List<ItemPecaResponseDTO> getPecas() {
        return pecas;
    }

    public void setPecas(List<ItemPecaResponseDTO> pecas) {
        this.pecas = pecas;
    }

}
