package com.crudCliente.crudCliente;


import com.crudCliente.crudCliente.dto.ClienteDTO;
import com.crudCliente.crudCliente.form.ClienteForm;
import com.crudCliente.crudCliente.form.ClienteFormParcial;
import com.crudCliente.crudCliente.repository.ClienteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:/scripts/delete.sql")
@Sql(scripts = "classpath:/scripts/cliente_import.sql")
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes = CrudClienteApplication.class)
@AutoConfigureMockMvc
public class ClienteControllerTest{

	protected PayloadExtractor payloadExtractor;

	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	private ClienteRepository repository;

	protected HttpMessageConverter mappingJackson2HttpMessageConverter;

	@BeforeEach
	public void init() {
		payloadExtractor = new PayloadExtractor(jsonMapper);
	}
	
	@Test
	public void deveRetornarTodosOsClientes() throws Exception {
		mockMvc.perform(get("/clientes"))
			.andExpect(status().isOk())
			.andDo(payloadExtractor)
			.andReturn();
		List<ClienteDTO> clientes = payloadExtractor.asListOf(ClienteDTO.class, true);
		assertEquals(4, clientes.size());
	}

	@Test
	public void deveRetornarApenasUmCliente() throws Exception {
		mockMvc.perform(get("/clientes")
				.param("nome", "tamara")
				.param("cpf", "60829891391"))
				.andExpect(status().isOk())
				.andDo(payloadExtractor)
				.andReturn();
		List<ClienteDTO> clientes = payloadExtractor.asListOf(ClienteDTO.class, true);
		assertEquals(1, clientes.size());
	}

	@Test
	public void deveRetornarSalvarUmRegistro() throws Exception {
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setNome("Joao");
		clienteForm.setCpf("11111111111");
		clienteForm.setDataNascimento(LocalDate.now());
		mockMvc.perform(post("/clientes")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.json(clienteForm)))
				.andExpect(status().isCreated())
				.andDo(payloadExtractor)
				.andReturn();


		assertEquals(5, repository.findAll().size());
	}


	@Test
	public void deveAtualizarUmRegistro() throws Exception {
		ClienteForm clienteForm = new ClienteForm();
		clienteForm.setId(1L);
		clienteForm.setNome("Joao");
		clienteForm.setCpf("11111111111");
		clienteForm.setDataNascimento(LocalDate.now());
		mockMvc.perform(put("/clientes/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.json(clienteForm)))
				.andExpect(status().isOk())
				.andDo(payloadExtractor)
				.andReturn();

		assertEquals("Joao",repository.findById(1L).get().getNome());
	}

	@Test
	public void deveAtualizarApenasNome() throws Exception {
		ClienteFormParcial clienteFormParcial = new ClienteFormParcial();
		clienteFormParcial.setNome("Pedro");
		mockMvc.perform(patch("/clientes/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.json(clienteFormParcial)))
				.andExpect(status().isOk())
				.andDo(payloadExtractor)
				.andReturn();

		assertEquals("Pedro",repository.findById(1L).get().getNome());
	}

	@Test
	public void deveDeletarUmRegistro() throws Exception {
		ClienteFormParcial clienteFormParcial = new ClienteFormParcial();
		clienteFormParcial.setNome("Pedro");
		mockMvc.perform(delete("/clientes/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(payloadExtractor)
				.andReturn();

		assertEquals(3,repository.findAll().size());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}

	@Autowired
	protected void setConverters(HttpMessageConverter<?>[] converters) {
		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
				.findAny()
				.orElse(null);
	}

}
