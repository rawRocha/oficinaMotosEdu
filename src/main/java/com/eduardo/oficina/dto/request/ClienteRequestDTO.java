package com.eduardo.oficina.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ClienteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    // Validação simples de CPF só dígitos e tamanho 11, ideal validar CPF com
    // lógica ou lib específica
    @Pattern(regexp = "\\d{11}", message = "O CPF deve conter 11 números")
    private String cpf;

    @NotBlank(message = "O contato é obrigatório")
    // Pode ser telefone ou email, aqui só valida que tem no mínimo 8 e máximo 15
    // caracteres (ex: telefone)
    @Size(min = 8, max = 15, message = "O contato deve ter entre 8 e 15 caracteres")
    private String contato;

    // getters e setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }
}
