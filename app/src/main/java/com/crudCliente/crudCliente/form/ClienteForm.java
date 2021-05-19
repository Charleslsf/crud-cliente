package com.crudCliente.crudCliente.form;

import com.crudCliente.crudCliente.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

@Getter @Setter
public class ClienteForm implements Serializable {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @Size(max = 11, min = 11)
    private String cpf;

    @NotNull
    private LocalDate dataNascimento;


    public void copyToModel(Cliente cliente) {
       cliente.setNome(nome);
       cliente.setCpf(cpf);
       cliente.setDataNascimento(dataNascimento);
    }

}
