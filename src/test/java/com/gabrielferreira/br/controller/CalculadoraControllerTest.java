package com.gabrielferreira.br.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.modelo.Calculadora;
import com.gabrielferreira.br.service.impl.CalculadoraServiceImpl;

@SpringBootTest
@ActiveProfiles("test") // Rodar com o perfil de teste, rodar com o ambiente de teste
@AutoConfigureMockMvc
public class CalculadoraControllerTest {
	
	private static String API_CALCULADORA = "/calculadora";
	private static MediaType JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean // Quando o contexto subir, não pode injetar o objeto real, so com os objetos falsos
	private CalculadoraServiceImpl calculadoraServiceImpl;
	
	private Calculadora calculadora;
	
	@BeforeEach
	private void criarInstancias() {
		// Cenário
		calculadora = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(15)).build();
	}
	
	@Test
	@DisplayName("Deve calcular a soma de valores.")
	public void deveCalcularSoma() throws Exception {
		
		// Cenário 
		Calculadora calculadoraResultadoTotal = Calculadora.builder().id(1L).primeiroValor(calculadora.getPrimeiroValor())
				.segundoValor(calculadora.getSegundoValor())
				.valorTotal(BigDecimal.valueOf(25)).build();
		
		// Execução 
		when(calculadoraServiceImpl.somar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(calculadoraResultadoTotal);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CALCULADORA + "/somar")
												.accept(JSON)
												.contentType(JSON)
												.content(json);
		mockMvc
			.perform(request)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").value(calculadoraResultadoTotal.getId()))
			.andExpect(jsonPath("primeiroValor").value(calculadoraResultadoTotal.getPrimeiroValor()))
			.andExpect(jsonPath("segundoValor").value(calculadoraResultadoTotal.getSegundoValor()))
			.andExpect(jsonPath("valorTotal").value(calculadoraResultadoTotal.getValorTotal()))
			.andExpect(jsonPath("tipoCalculo").value(calculadoraResultadoTotal.getTipoCalculo()));
			
	}
	
	@Test
	@DisplayName("Não deve calcular soma, pois um dos valores é negativo.")
	public void naoDeveCalcularSoma() throws Exception {
		
		// Cenário e executando o método de somar com o mock
		Calculadora calculadora = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(-20)).build();
		when(calculadoraServiceImpl.somar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor()))
			.thenThrow(new RegraException("Não deve somar com valores negativos."));
		
		// Transformar o objeto em json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CALCULADORA + "/somar").accept(JSON).contentType(JSON).content(json);
				
		// Fazendo o teste e verificando
		mockMvc.perform(request).andDo(print())
						.andExpect(status().isBadRequest())
						.andExpect(jsonPath("mensagem", equalTo("Não deve somar com valores negativos.")));
	}
	
	@Test
	public void deveCalcularSubtracao() throws Exception {
		
		// Execução 
		Calculadora calculadoraResultadoTotal = Calculadora.builder().id(1L).primeiroValor(calculadora.getPrimeiroValor())
				.segundoValor(calculadora.getSegundoValor())
				.valorTotal(BigDecimal.valueOf(-5)).build();
		
		when(calculadoraServiceImpl.subtrair(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(calculadoraResultadoTotal);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CALCULADORA + "/subtrair")
												.accept(JSON)
												.contentType(JSON)
												.content(json);
		mockMvc
			.perform(request)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").value(calculadoraResultadoTotal.getId()))
			.andExpect(jsonPath("primeiroValor").value(calculadoraResultadoTotal.getPrimeiroValor()))
			.andExpect(jsonPath("segundoValor").value(calculadoraResultadoTotal.getSegundoValor()))
			.andExpect(jsonPath("valorTotal").value(calculadoraResultadoTotal.getValorTotal()));
			
	}
	
	@Test
	public void deveCalcularDivisao() throws Exception {
		
		// Execução 
		Calculadora calculadoraResultadoTotal = Calculadora.builder().id(1L).primeiroValor(BigDecimal.valueOf(10))
				.segundoValor(BigDecimal.valueOf(2))
				.valorTotal(BigDecimal.valueOf(5)).build();
		
		when(calculadoraServiceImpl.divisao(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(calculadoraResultadoTotal);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CALCULADORA + "/divisao")
												.accept(JSON)
												.contentType(JSON)
												.content(json);
		mockMvc
			.perform(request)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").value(calculadoraResultadoTotal.getId()))
			.andExpect(jsonPath("primeiroValor").value(calculadoraResultadoTotal.getPrimeiroValor()))
			.andExpect(jsonPath("segundoValor").value(calculadoraResultadoTotal.getSegundoValor()))
			.andExpect(jsonPath("valorTotal").value(calculadoraResultadoTotal.getValorTotal()));
			
	}
	
	@Test
	@DisplayName("Não deve calculae divisão, pois o segundo número é 0.")
	public void naoDeveCalcularDivisao() throws Exception {
		
		// Cenário e executando o método de somar com o mock
		Calculadora calculadora = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(10))
				.segundoValor(BigDecimal.valueOf(0)).build();
		
		when(calculadoraServiceImpl.somar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor()))
				.thenThrow(new RegraException("Não é possível dividir com o valor 0."));

		// Transformar o objeto em json
		String json = new ObjectMapper().writeValueAsString(calculadora);

		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CALCULADORA + "/somar").accept(JSON)
				.contentType(JSON).content(json);

		// Fazendo o teste e verificando
		mockMvc.perform(request).andDo(print()).andExpect(status().isBadRequest())
				.andExpect(jsonPath("mensagem", equalTo("Não é possível dividir com o valor 0.")));
	}
	
	@Test
	@DisplayName("Deve calcular a multiplicação.")
	public void deveCalcularMultiplicacao() throws Exception {
		
		// Cenário e executando o método de somar com o mock
		Calculadora calculadora = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(10))
						.segundoValor(BigDecimal.valueOf(0)).valorTotal(BigDecimal.valueOf(0)).build();
		
		when(calculadoraServiceImpl.multiplicar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(calculadora);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CALCULADORA + "/multiplicacao")
												.accept(JSON)
												.contentType(JSON)
												.content(json);
		mockMvc
			.perform(request)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(jsonPath("id").value(calculadora.getId()))
			.andExpect(jsonPath("primeiroValor").value(calculadora.getPrimeiroValor()))
			.andExpect(jsonPath("segundoValor").value(calculadora.getSegundoValor()))
			.andExpect(jsonPath("valorTotal").value(calculadora.getValorTotal()));
			
	}
	
	@Test
	@DisplayName("Deve mostrar lista de calculos realizados.")
	public void deveMostrarListagensCalculos() throws Exception{
		// Cenário 
    	List<Calculadora> calculadoras = new ArrayList<>();
    	calculadoras.add(Calculadora.builder().id(1L).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(3))
    			.valorTotal(BigDecimal.valueOf(30)).tipoCalculo("Multiplicação")
    			.build());
    	calculadoras.add(Calculadora.builder().id(2L).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(3))
    			.valorTotal(BigDecimal.valueOf(13)).tipoCalculo("Soma")
    			.build());
    	calculadoras.add(Calculadora.builder().id(3L).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(3))
    			.valorTotal(BigDecimal.valueOf(7)).tipoCalculo("Subtração")
    			.build());
    	
    	// Mock para retornar os dados de cima 
    	when(calculadoraServiceImpl.listagensCalculos()).thenReturn(calculadoras);
    	
    	// Criar um requisição do tipo get
    	MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(API_CALCULADORA)
    												.accept(JSON)
    												.contentType(JSON);
    	
    	// Verificando 
    	mockMvc
    		.perform(request)
    		.andDo(print())
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$[*]",Matchers.hasSize(3)));
	}

}
