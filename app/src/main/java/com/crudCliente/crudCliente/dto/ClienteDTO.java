package com.crudCliente.crudCliente.dto;

import com.crudCliente.crudCliente.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Getter @Setter
public class ClienteDTO implements Serializable {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    private Integer idade;

    public static ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setCpf(cliente.getCpf());
        dto.setDataNascimento(cliente.getDataNascimento());
        dto.setIdade(getIdade(cliente.getDataNascimento()));
        return dto;
    }

    private static Integer getIdade(LocalDate dataNascimento) {
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

}
