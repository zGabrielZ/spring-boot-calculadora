package com.gabrielferreira.br.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabrielferreira.br.modelo.Calculadora;
import com.gabrielferreira.br.service.impl.CalculadoraServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculadoraControllerTest {
	
	private static String API_CURSO = "/calculadora";
	private static MediaType JSON = MediaType.APPLICATION_JSON;
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean // Quando o contexto subir, não pode injetar o objeto real, so com os objetos falsos
	private CalculadoraServiceImpl calculadoraServiceImpl;
	
	private Calculadora calculadora;
	
	@BeforeEach
	private void criarInstancias() {
		// Cenário
		calculadora = Calculadora.builder().id(null).primeiroValor(any()).segundoValor(any()).build();
	}
	
	@Test
	public void deveCalcularSoma() throws Exception {
		
		// Execução 
		BigDecimal resultadoTotal = BigDecimal.valueOf(20);
		when(calculadoraServiceImpl.somar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(resultadoTotal);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CURSO + "/somar")
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
			.andExpect(jsonPath("valorTotal").value(resultadoTotal));
			
	}
	
	@Test
	public void deveCalcularSubtracao() throws Exception {
		// Execução 
		BigDecimal resultadoTotal = BigDecimal.valueOf(40);
		when(calculadoraServiceImpl.subtrair(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(resultadoTotal);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CURSO + "/subtrair")
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
			.andExpect(jsonPath("valorTotal").value(resultadoTotal));
			
	}
	
	@Test
	public void deveCalcularDivisao() throws Exception {
		// Execução 
		BigDecimal resultadoTotal = BigDecimal.valueOf(70);
		when(calculadoraServiceImpl.divisao(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(resultadoTotal);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CURSO + "/divisao")
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
			.andExpect(jsonPath("valorTotal").value(resultadoTotal));
			
	}
	
	@Test
	public void deveCalcularMultiplicacao() throws Exception {
		// Execução 
		BigDecimal resultadoTotal = BigDecimal.valueOf(100);
		when(calculadoraServiceImpl.multiplicar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor())).thenReturn(resultadoTotal);
		
		// Transforar objeto em uma string json
		String json = new ObjectMapper().writeValueAsString(calculadora);
		
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CURSO + "/multiplicacao")
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
			.andExpect(jsonPath("valorTotal").value(resultadoTotal));
			
	}

}
