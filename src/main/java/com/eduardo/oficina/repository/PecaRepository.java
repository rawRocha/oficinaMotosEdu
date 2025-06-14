package com.eduardo.oficina.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardo.oficina.model.Peca;

@Repository
public interface PecaRepository extends JpaRepository<Peca, Long> {
    Optional<Peca> findByNomeAndMarcaAndModeloVeiculo(String nome, String marca, String modeloVeiculo);

    List<Peca> findByNomeContainingIgnoreCase(String nome);
}
