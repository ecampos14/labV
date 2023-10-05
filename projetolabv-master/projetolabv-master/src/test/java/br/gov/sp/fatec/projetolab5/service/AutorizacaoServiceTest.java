package br.gov.sp.fatec.projetolab5.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.gov.sp.fatec.projetolab5.entity.Autorizacao;
import br.gov.sp.fatec.projetolab5.repository.AutorizacaoRepository;

@SpringBootTest
public class AutorizacaoServiceTest {

    @Autowired
    private AutorizacaoService autorizacaoService;

    @MockBean
    private AutorizacaoRepository autorizacaoRepository;

    @Test
    public void buscarPeloNomeTest() {
        Autorizacao autorizacao = new Autorizacao();
        autorizacao.setId(1L);
        autorizacao.setNome("Teste");

        Mockito.when(autorizacaoRepository.getByNome("Teste")).thenReturn(autorizacao);

        Autorizacao resultado = autorizacaoService.buscarPeloNome("Teste");

        assertEquals("Teste", resultado.getNome());
    }

    @Test
    public void buscarTodasTest() {
    	    // Criar uma lista fictícia de Autorizacoes
    	    List<Autorizacao> autorizacoes = new ArrayList<>();
    	    Autorizacao autorizacao1 = new Autorizacao();
    	    autorizacao1.setId(1L);
    	    autorizacao1.setNome("Autorizacao1");

    	    Autorizacao autorizacao2 = new Autorizacao();
    	    autorizacao2.setId(2L);
    	    autorizacao2.setNome("Autorizacao2");

    	    autorizacoes.add(autorizacao1);
    	    autorizacoes.add(autorizacao2);

    	    Mockito.when(autorizacaoRepository.findAll()).thenReturn(autorizacoes);

    	    List<Autorizacao> resultado = autorizacaoService.buscarTodas();

    	    assertEquals(2, resultado.size());
    	}


    @Test
    public void novaAutorizacaoTest() {
        Mockito.when(autorizacaoRepository.save(any())).thenAnswer(invocation -> {
            Autorizacao autorizacaoSalva = invocation.getArgument(0);
            autorizacaoSalva.setId(1L);
            return autorizacaoSalva;
        });

        Autorizacao resultado = autorizacaoService.novaAutorizacao("NovaAutorizacao");

        assertEquals("NovaAutorizacao", resultado.getNome());
        assertEquals(1L, resultado.getId().longValue());
    }

    @Test
    public void novaAutorizacaoParametrosInvalidosTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            autorizacaoService.novaAutorizacao("");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            autorizacaoService.novaAutorizacao(null);
        });
    }
}
