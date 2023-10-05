package br.gov.sp.fatec.projetolab5.controller;

import java.time.LocalDateTime;
import org.springframework.http.MediaType;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.fatec.projetolab5.entity.Anotacao;
import br.gov.sp.fatec.projetolab5.entity.Usuario;
import br.gov.sp.fatec.projetolab5.service.AnotacaoService;

@SpringBootTest
@AutoConfigureMockMvc
public class AnotacaoControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AnotacaoService service;

	@Test
	public void testNovaAnotacao_Valida() throws Exception {
		String textoTeste = "Texto de teste válido";
		String usuarioTeste = "UsuarioTeste";

		
		Usuario usuarioSimulado = new Usuario();
		usuarioSimulado.setId(1L);
		usuarioSimulado.setNome(usuarioTeste);

		Anotacao anotacaoSimulada = new Anotacao(textoTeste, LocalDateTime.now(), usuarioSimulado);

		Mockito.when(service.novaAnotacao(textoTeste, usuarioTeste)).thenReturn(anotacaoSimulada);

		mockMvc.perform(MockMvcRequestBuilders.post("/anotacao/novo/" + textoTeste + "/" + usuarioTeste)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("/* JSON esperado para a anotação simulada */"));
	}

	@Test
	public void testNovaAnotacao_TextoMuitoCurto() throws Exception {
		String textoTeste = "Curto";
		String usuarioTeste = "UsuarioTeste";

		Mockito.when(service.novaAnotacao(textoTeste, usuarioTeste))
				.thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Texto muito curto!"));

		mockMvc.perform(MockMvcRequestBuilders.post("/anotacao/novo/" + textoTeste + "/" + usuarioTeste)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void testNovaAnotacao_UsuarioNaoEncontrado() throws Exception {
		String textoTeste = "Texto de teste";
		String usuarioTeste = "UsuarioInexistente";

		Mockito.when(service.novaAnotacao(textoTeste, usuarioTeste))
				.thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));

		mockMvc.perform(MockMvcRequestBuilders.post("/anotacao/novo/" + textoTeste + "/" + usuarioTeste)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isNotFound());
	}
}
