package com.eduardo.oficina.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.Year;

public class MotoRequestDTO {

    @NotBlank(message = "Marca é obrigatória")
    @Size(min = 2, max = 50, message = "Marca deve ter entre 2 e 50 caracteres")
    private String marca;

    @NotBlank(message = "Modelo é obrigatório")
    @Size(min = 2, max = 50, message = "Modelo deve ter entre 2 e 50 caracteres")
    private String modelo;

    @NotBlank(message = "Placa é obrigatória")
    // Regex para placa antiga: 3 letras + 4 números, ex: ABC1234
    @Pattern(regexp = "^[A-Z]{3}\\d{4}$", message = "Placa deve estar no formato ABC1234")
    private String placa;

    @Min(value = 1900, message = "Ano deve ser maior ou igual a 1900")
    @Max(value = Year.MAX_VALUE, message = "Ano inválido")
    private int ano;

    @NotNull(message = "ID do cliente é obrigatório")
    private Long idCliente;

    // getters e setters

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
