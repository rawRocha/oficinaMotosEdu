package com.eduardo.oficina.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.eduardo.oficina.enums.StatusOrdemServico;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ordens_servico")
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "moto_id", nullable = false)
    private Moto moto;

    @Column(columnDefinition = "TEXT")
    private String descricaoProblema;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;

    @Column(precision = 10, scale = 2)
    private BigDecimal valorTotal;

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoRealizado> servicos = new ArrayList<>();

    @OneToMany(mappedBy = "ordemServico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemPeca> pecas = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime dataAbertura;

    @UpdateTimestamp
    private LocalDateTime dataAtualizacao;

    public LocalDateTime getCreatedAt() {
        return dataAbertura;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.dataAbertura = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return dataAtualizacao;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.dataAtualizacao = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Moto getMoto() {
        return moto;
    }

    public void setMoto(Moto moto) {
        this.moto = moto;
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

    public List<ServicoRealizado> getServicos() {
        return servicos;
    }

    public void setServicos(List<ServicoRealizado> servicos) {
        this.servicos = servicos;
    }

    public List<ItemPeca> getPecas() {
        return pecas;
    }

    public void setPecas(List<ItemPeca> pecas) {
        this.pecas = pecas;
    }

}
