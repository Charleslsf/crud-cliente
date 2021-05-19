package com.crudCliente.crudCliente.service;

import com.crudCliente.crudCliente.dto.ClienteDTO;
import com.crudCliente.crudCliente.exception.ResourceNotFoundException;
import com.crudCliente.crudCliente.form.ClienteForm;
import com.crudCliente.crudCliente.form.ClienteFormParcial;
import com.crudCliente.crudCliente.model.Cliente;
import com.crudCliente.crudCliente.repository.ClienteRepository;
import com.crudCliente.crudCliente.repository.filter.ClienteFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.crudCliente.crudCliente.repository.spec.ClienteSpec.withFilter;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Page<Cliente> findByFilter(ClienteFilter filter, Pageable pageable) {
        return repository.findAll(withFilter(filter), pageable);
    }

    public void update(Long id, ClienteForm clienteForm) {
        Cliente cliente = getClienteById(id);
        clienteForm.copyToModel(cliente);
        repository.save(cliente);
    }

    public ClienteDTO save(ClienteForm clienteForm) {
        Cliente cliente = new Cliente();
        clienteForm.copyToModel(cliente);
        return ClienteDTO.toDTO(repository.save(cliente));
    }

    public void updateParcial(Long id, ClienteFormParcial clienteFormParcial) {
        Cliente cliente = getClienteById(id);
        clienteFormParcial.copyToModel(cliente);
        repository.save(cliente);
    }

    public void delete(Long id) {
        getClienteById(id);
        repository.deleteById(id);
    }

    private Cliente getClienteById(Long id) {
        Optional<Cliente> clienteOptional = repository.findById(id);
        return clienteOptional.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado."));
    }


}
