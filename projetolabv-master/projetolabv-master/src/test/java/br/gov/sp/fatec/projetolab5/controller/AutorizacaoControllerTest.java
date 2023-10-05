package br.gov.sp.fatec.projetolab5.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.gov.sp.fatec.projetolab5.entity.Autorizacao;
import br.gov.sp.fatec.projetolab5.service.AutorizacaoService;

@SpringBootTest
public class AutorizacaoControllerTest {
	private AutorizacaoController controller;
	private AutorizacaoService service;

	@BeforeEach
	public void setUp() {
		service = mock(AutorizacaoService.class);
		controller = new AutorizacaoController(service);
	}

	@Test
	public void testBuscarTodas() {
		List<Autorizacao> autorizacoesMocadas = new ArrayList<>();
		Autorizacao autorizacao1 = new Autorizacao();
		autorizacao1.setNome("Autorizacao1");
		autorizacoesMocadas.add(autorizacao1);

		Autorizacao autorizacao2 = new Autorizacao();
		autorizacao2.setNome("Autorizacao2");
		autorizacoesMocadas.add(autorizacao2);

		when(service.buscarTodas()).thenReturn(autorizacoesMocadas);

		List<Autorizacao> result = controller.buscarTodas();
	}

	@Test
	public void testBuscarPeloNome() {
		String nomeMocado = "AutorizacaoMocada";
		Autorizacao autorizacaoMocada = new Autorizacao();
		autorizacaoMocada.setNome(nomeMocado);

		when(service.buscarPeloNome(nomeMocado)).thenReturn(autorizacaoMocada);

		Autorizacao result = controller.buscarPeloNome(nomeMocado);
	}

	@Test
	public void testNovaAutorizacao() {
		String nomeMocado = "NovaAutorizacaoMocada";
		Autorizacao autorizacaoMocada = new Autorizacao();
		autorizacaoMocada.setNome(nomeMocado);

		when(service.novaAutorizacao(nomeMocado)).thenReturn(autorizacaoMocada);

		Autorizacao result = controller.novaAutorizaco(nomeMocado);
	}
}
