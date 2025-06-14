package com.eduardo.oficina.mapper;

import com.eduardo.oficina.dto.response.*;
import com.eduardo.oficina.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class OrdemServicoMapper {

    public static OrdemServicoResponseDTO toResponseDTO(OrdemServico ordem) {
        OrdemServicoResponseDTO dto = new OrdemServicoResponseDTO();
        dto.setId(ordem.getId());
        dto.setMotoId(ordem.getMoto().getId());
        dto.setPlacaMoto(ordem.getMoto().getPlaca());
        dto.setDescricaoProblema(ordem.getDescricaoProblema());
        dto.setObservacoes(ordem.getObservacoes());
        dto.setStatus(ordem.getStatus());
        dto.setValorTotal(ordem.getValorTotal());
        dto.setDataAbertura(ordem.getCreatedAt());
        dto.setDataAtualizacao(ordem.getUpdatedAt());

        List<ServicoResponseDTO> servicos = ordem.getServicos().stream().map(s -> {
            ServicoResponseDTO sDto = new ServicoResponseDTO();
            sDto.setId(s.getId());
            sDto.setDescricao(s.getDescricao());
            sDto.setPreco(s.getPreco());
            return sDto;
        }).collect(Collectors.toList());

        dto.setServicos(servicos);

        List<ItemPecaResponseDTO> pecas = ordem.getPecas().stream().map(p -> {
            ItemPecaResponseDTO pDto = new ItemPecaResponseDTO();
            pDto.setId(p.getId());
            pDto.setNomePeca(p.getPeca().getNome());
            pDto.setQuantidade(p.getQuantidade());
            pDto.setPrecoUnitario(p.getPrecoUnitario());
            return pDto;
        }).collect(Collectors.toList());

        dto.setPecas(pecas);

        return dto;
    }
}
