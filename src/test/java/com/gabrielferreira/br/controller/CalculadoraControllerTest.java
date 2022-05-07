package com.gabrielferreira.br.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
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
import com.gabrielferreira.br.modelo.dto.CalculadoraDTO;
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
	
	@Test
	@DisplayName("Deve calcular a soma de valores.")
	public void deveCalcularSoma() throws Exception {
		
		// Cenário 
		CalculadoraDTO calculadora = CalculadoraDTO.builder().id(1L).primeiroValor(BigDecimal.valueOf(10))
				.segundoValor(BigDecimal.valueOf(20))
				.valorTotal(BigDecimal.valueOf(30))
				.tipoCalculo("Soma").build();
		
		// Mock da execução 
		when(calculadoraServiceImpl.somar(any())).thenReturn(calculadora);
		
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
			.andExpect(jsonPath("id").value(calculadora.getId()))
			.andExpect(jsonPath("primeiroValor").value(calculadora.getPrimeiroValor()))
			.andExpect(jsonPath("segundoValor").value(calculadora.getSegundoValor()))
			.andExpect(jsonPath("valorTotal").value(calculadora.getValorTotal()))
			.andExpect(jsonPath("tipoCalculo").value(calculadora.getTipoCalculo()));
			
	}
	
	@Test
	@DisplayName("Não deve calcular soma, pois um dos valores é negativo.")
	public void naoDeveCalcularSoma() throws Exception {
		
		// Cenário
		CalculadoraDTO calculadora = CalculadoraDTO.builder().id(1L).primeiroValor(BigDecimal.valueOf(-10))
				.segundoValor(BigDecimal.valueOf(20))
				.valorTotal(BigDecimal.valueOf(10))
				.tipoCalculo("Soma").build();
		
		// Mock da execução 
		when(calculadoraServiceImpl.somar(any()))
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
	@DisplayName("Deve calcular subtração")
	public void deveCalcularSubtracao() throws Exception {
		
		// Cenário 
		CalculadoraDTO calculadora = CalculadoraDTO.builder().id(1L).primeiroValor(BigDecimal.valueOf(20))
				.segundoValor(BigDecimal.valueOf(20))
				.valorTotal(BigDecimal.valueOf(0))
				.tipoCalculo("Subtração").build();
		
		// Mock com valores de cima
		when(calculadoraServiceImpl.subtrair(any())).thenReturn(calculadora);
		
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
			.andExpect(jsonPath("id").value(calculadora.getId()))
			.andExpect(jsonPath("primeiroValor").value(calculadora.getPrimeiroValor()))
			.andExpect(jsonPath("segundoValor").value(calculadora.getSegundoValor()))
			.andExpect(jsonPath("valorTotal").value(calculadora.getValorTotal()))
			.andExpect(jsonPath("tipoCalculo").value(calculadora.getTipoCalculo()));
			
	}
	
	@Test
	@DisplayName("Deve calcular divisão")
	public void deveCalcularDivisao() throws Exception {
		
		// Cenário 
		CalculadoraDTO calculadora = CalculadoraDTO.builder().id(1L).primeiroValor(BigDecimal.valueOf(20))
				.segundoValor(BigDecimal.valueOf(20))
				.valorTotal(BigDecimal.valueOf(1))
				.tipoCalculo("Divisão").build();
		
		// Mock com valores de cima
		when(calculadoraServiceImpl.divisao(any())).thenReturn(calculadora);
		
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
			.andExpect(jsonPath("id").value(calculadora.getId()))
			.andExpect(jsonPath("primeiroValor").value(calculadora.getPrimeiroValor()))
			.andExpect(jsonPath("segundoValor").value(calculadora.getSegundoValor()))
			.andExpect(jsonPath("valorTotal").value(calculadora.getValorTotal()))
			.andExpect(jsonPath("tipoCalculo").value(calculadora.getTipoCalculo()));
			
	}
	
	@Test
	@DisplayName("Não deve fazer a divisão, pois o segundo número é 0.")
	public void naoDeveCalcularDivisao() throws Exception {
		
		// Cenário
		CalculadoraDTO calculadora = CalculadoraDTO.builder().id(1L).primeiroValor(BigDecimal.valueOf(20))
				.segundoValor(BigDecimal.valueOf(0))
				.valorTotal(BigDecimal.valueOf(0))
				.tipoCalculo("Divisão").build();
		
		// Mock com valores de cima
		when(calculadoraServiceImpl.somar(any()))
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
		
		// Cenário
		CalculadoraDTO calculadora = CalculadoraDTO.builder().id(1L).primeiroValor(BigDecimal.valueOf(2))
				.segundoValor(BigDecimal.valueOf(2))
				.valorTotal(BigDecimal.valueOf(4))
				.tipoCalculo("Multiplicação").build();
		
		// Mock com valores de cima
		when(calculadoraServiceImpl.multiplicar(any())).thenReturn(calculadora);
		
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
			.andExpect(jsonPath("valorTotal").value(calculadora.getValorTotal()))
			.andExpect(jsonPath("tipoCalculo").value(calculadora.getTipoCalculo()));
			
	}
	
	@Test
	@DisplayName("Deve mostrar lista de calculos realizados.")
	public void deveMostrarListagensCalculos() throws Exception{
		// Cenário 
    	List<CalculadoraDTO> calculadoras = new ArrayList<>();
    	calculadoras.add(CalculadoraDTO.builder().id(1L).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(3))
    			.valorTotal(BigDecimal.valueOf(30)).tipoCalculo("Multiplicação")
    			.build());
    	calculadoras.add(CalculadoraDTO.builder().id(2L).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(3))
    			.valorTotal(BigDecimal.valueOf(13)).tipoCalculo("Soma")
    			.build());
    	calculadoras.add(CalculadoraDTO.builder().id(3L).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(3))
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
	
	@Test
	@DisplayName("Não deve calcular soma pois não tem dados suficientes.")
	public void naoDeveCalcularSomaValoresVazios() throws Exception{
		// Cenário
		CalculadoraDTO calculadora = CalculadoraDTO.builder().id(1L).primeiroValor(null)
				.segundoValor(null)
				.valorTotal(null)
				.tipoCalculo(null).build();
		
		// Transformar o objto em json
		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(calculadora);
						
		// Criar uma requisição do tipo post
		MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(API_CALCULADORA + "/somar").accept(JSON).contentType(JSON).content(json);
				
		// Verificar a requisição
		mockMvc.perform(request)
					.andDo(print())
					.andExpect(status().isBadRequest())
					.andExpect(jsonPath("listaErrosCampos",Matchers.hasSize(2)));
	}

}
