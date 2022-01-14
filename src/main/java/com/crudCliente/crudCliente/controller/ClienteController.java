package com.crudCliente.crudCliente.controller;

import com.crudCliente.crudCliente.dto.ClienteDTO;
import com.crudCliente.crudCliente.form.ClienteForm;
import com.crudCliente.crudCliente.form.ClienteFormParcial;
import com.crudCliente.crudCliente.repository.filter.ClienteFilter;
import com.crudCliente.crudCliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@GetMapping(produces="application/json")
	public Page<ClienteDTO> find(ClienteFilter filtro, @PageableDefault() Pageable pageable) {
		return clienteService.findByFilter(filtro, pageable).map(ClienteDTO::toDTO);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Long id,  @RequestBody @Valid ClienteForm clienteForm) {
		clienteService.update(id, clienteForm);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClienteDTO create(@RequestBody @Valid ClienteForm clienteForm) {
		return clienteService.save(clienteForm);
	}

	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void partialUpdate(@PathVariable Long id, @RequestBody ClienteFormParcial clienteFormParcial) {
		clienteService.updateParcial(id, clienteFormParcial);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long id) {
		clienteService.delete(id);
	}
}
