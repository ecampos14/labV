package br.gov.sp.fatec.projetolab5.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import br.gov.sp.fatec.projetolab5.entity.Anotacao;
import br.gov.sp.fatec.projetolab5.service.AnotacaoService;

@SpringBootTest
@AutoConfigureMockMvc
public class AnotacaoControllerTest {

    private AnotacaoController controller;
    private AnotacaoService service;

    @BeforeEach
    public void setUp() {
        service = mock(AnotacaoService.class);
        controller = new AnotacaoController(service);
    }

    @Test
    public void testBuscarPorTexto() {
        String textoMocado = "AnotacaoMocada";
        List<Anotacao> anotacoesMocadas = new ArrayList<>();
        anotacoesMocadas.add(new Anotacao());
        anotacoesMocadas.add(new Anotacao());

        when(service.buscaAnotacoes(textoMocado)).thenReturn(anotacoesMocadas);

        List<Anotacao> result = controller.buscarPorTexto(textoMocado);

        assertEquals(anotacoesMocadas, result);
    }

    @Test
    public void testNovaAnotacao() {
        Anotacao anotacaoMocada = new Anotacao(); 

        when(service.novaAnotacao(any(Anotacao.class))).thenReturn(anotacaoMocada);

        Anotacao result = controller.novaAnotacao(anotacaoMocada);

        assertEquals(anotacaoMocada, result);
    }
}

