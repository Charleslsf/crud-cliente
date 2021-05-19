package com.crudCliente.crudCliente.form;

import com.crudCliente.crudCliente.model.Cliente;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.Objects;

@Getter @Setter
public class ClienteFormParcial {

    private Long id;

    private String nome;

    private String cpf;

    private LocalDate dataNascimento;

    public void copyToModel(Cliente cliente) {
        if(StringUtils.isNotBlank(nome)) {
            cliente.setNome(nome);
        }
        if(StringUtils.isNotBlank(cpf)) {
            cliente.setCpf(cpf);
        }
        if(Objects.nonNull(dataNascimento)) {
            cliente.setDataNascimento(dataNascimento);
        }
    }

}
