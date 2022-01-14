package com.crudCliente.crudCliente.repository.filter;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class ClienteFilter {

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private LocalDateTime dataCriacao;
}
