package com.eduardo.oficina.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eduardo.oficina.model.OrdemServico;

public interface OrdemServicoRepository extends JpaRepository<OrdemServico, Long> {
    List<OrdemServico> findByMotoId(Long motoId);
}