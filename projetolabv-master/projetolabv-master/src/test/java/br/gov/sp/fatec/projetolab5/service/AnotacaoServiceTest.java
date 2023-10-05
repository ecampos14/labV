package br.gov.sp.fatec.projetolab5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import br.gov.sp.fatec.projetolab5.entity.Anotacao;
import br.gov.sp.fatec.projetolab5.entity.Usuario;
import br.gov.sp.fatec.projetolab5.repository.AnotacaoRepository;
import br.gov.sp.fatec.projetolab5.repository.UsuarioRepository;

@SpringBootTest
public class AnotacaoServiceTest {
	@Autowired
	private AnotacaoService service;

	@Autowired
	private AnotacaoRepository anotacaoRepo;

	@Autowired
	private UsuarioRepository usuarioRepo;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testNovaAnotacao() {
		String texto = "ManuTeste";
		String usuario1 = "ManuTeste";
		Usuario usuario = new Usuario();
		usuario.setId(1L);
		usuario.setNome(usuario1);

		Anotacao anotacao1 = new Anotacao();
		anotacao1.setId(1L);
		anotacao1.setTexto(texto);
		anotacao1.setDataHora(LocalDateTime.now());
		anotacao1.setUsuario(usuario);

		when(usuarioRepo.findByNome(usuario1)).thenReturn(Optional.of(usuario));
		when(anotacaoRepo.save(any(Anotacao.class))).thenReturn(anotacao1);

		Anotacao result = service.novaAnotacao(texto, usuario1);

		assertEquals(anotacao1, result);
	}

	@Test
	public void testNovaAnotacaoComTextoNulo() {
		String usuario = "ManuTeste";

		assertThrows(ResponseStatusException.class, () -> {
			service.novaAnotacao(null, usuario);
		});
	}

	@Test
	public void testNovaAnotacaoComUsuarioNulo() {
		String texto = "ManuTeste";

		assertThrows(ResponseStatusException.class, () -> {
			service.novaAnotacao(texto, null);
		});
	}

	@Test
	public void testNovaAnotacaoComTextoCurto() {
		String texto = "ManuTeste";
		String usuario = "ManuTeste";

		assertThrows(ResponseStatusException.class, () -> {
			service.novaAnotacao(texto, usuario);
		});
	}

	@Test
	public void testNovaAnotacaoComUsuarioNaoEncontrado() {
		String texto = "ManuTeste";
		String usuario = "ManuTeste";

		when(usuarioRepo.findByNome(usuario)).thenReturn(Optional.empty());

		assertThrows(ResponseStatusException.class, () -> {
			service.novaAnotacao(texto, usuario);
		});
	}

}
