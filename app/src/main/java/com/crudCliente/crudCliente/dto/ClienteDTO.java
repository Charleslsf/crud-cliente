package com.crudCliente.crudCliente.dto;

import com.crudCliente.crudCliente.model.Cliente;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

@Getter @Setter
public class ClienteDTO implements Serializable {

    @ApiModelProperty(value = "nome", example = "Charles")
    private String nome;

    @ApiModelProperty(value = "cpf", example = "60829891390")
    private String cpf;

    @ApiModelProperty(value = "dataNascimento", example = "1993-08-23")
    private LocalDate dataNascimento;

    @ApiParam(hidden = true)
    private Integer idade;

    public static ClienteDTO toDTO(Cliente cliente) {
        ClienteDTO dto = new ClienteDTO();
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
