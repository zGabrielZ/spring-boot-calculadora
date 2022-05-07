package com.gabrielferreira.br.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.modelo.Calculadora;
import com.gabrielferreira.br.modelo.dto.CalculadoraDTO;
import com.gabrielferreira.br.repositorio.CalculadoraRepositorio;
import com.gabrielferreira.br.service.impl.CalculadoraServiceImpl;

@SpringBootTest
@ActiveProfiles("test") // Rodar com o perfil de teste, rodar com o ambiente de teste
public class CalculadoraServiceTest {

	private CalculadoraRepositorio calculadoraRepositorio;
	private CalculadoraServiceImpl calculadoraServiceImpl;
	
	// Utilizando o AssertJ -> https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html
	
	@BeforeEach
	public void criarInstancias() {
		calculadoraRepositorio = Mockito.mock(CalculadoraRepositorio.class);
		calculadoraServiceImpl = new CalculadoraServiceImpl(calculadoraRepositorio);
	}
	
    @Test
    @DisplayName("Deve somar com os dois números somente positivos.")
    public void deveSomarComNumerosPositivos(){
        // Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(5)).build();
    	
        // Execução
        CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.somar(criarCalculadora);
        
        // Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
        
        // Verificando o valor numérico
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(15));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(20));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isPositive();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Soma");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(15));
    }
    
    @Test
    @DisplayName("Não deve somar com um número negativo pois está implementado na regra de negócio.")
    public void naoDeveSomarComUmNumeroNegativo() {
    	// Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(10)).build();
    	
    	// Execução 
    	Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RegraException.class, () -> calculadoraServiceImpl.somar(criarCalculadora));
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio, never()).save(any());
        
        // Verificação se a instancia do erro foi o RegraException
     	assertThat(exception).isInstanceOf(RegraException.class).hasMessage("Não deve somar com valores negativos.");
        
    }
    
    @Test
    @DisplayName("Não deve somar com dois número negativos pois está implementado na regra de negócio.")
    public void naoDeveSomarComDoisNumeroNegativo() {
    	// Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(-10)).build();
    	
    	// Execução 
    	Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RegraException.class, () -> calculadoraServiceImpl.somar(criarCalculadora));
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio, never()).save(any());
        
        // Verificação se a instancia do erro foi o RegraException
     	assertThat(exception).isInstanceOf(RegraException.class).hasMessage("Não deve somar com valores negativos.");
    }
    
    @Test
    @DisplayName("Deve subtrair os dois números positivos.")
    public void deveSubtrairComNumerosPositivos() {
    	// Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(20)).segundoValor(BigDecimal.valueOf(3)).build();
    	
    	// Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.subtrair(criarCalculadora);
    	
    	 // Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
    	// Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(17));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(20));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isPositive();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Subtração");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(17));
    }
    
    @Test
    @DisplayName("Deve subtrair com um número negativo.")
    public void deveSubtrairComUmNumeroNegativo() {
    	// Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-20)).segundoValor(BigDecimal.valueOf(10)).build();
    	
    	// Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.subtrair(criarCalculadora);
    	
    	 // Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
    	// Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-30));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(-40), BigDecimal.valueOf(-20));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-50));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isNegative();	
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Subtração");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-30));
    }
    
    @Test
    @DisplayName("Deve subtrair com dois número negativos.")
    public void deveSubtrairComDoisNumeroNegativo() {
    	// Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-20)).segundoValor(BigDecimal.valueOf(-10)).build();
    	
    	// Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.subtrair(criarCalculadora);
    	
    	 // Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
    	// Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-10));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(-30), BigDecimal.valueOf(-6));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-12));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isNegative();	
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Subtração");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-10));
    }
    
    @Test
    @DisplayName("Deve dividir com dois números positivos.")
    public void deveDividirComNumerosPositivos(){
        // Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(12)).segundoValor(BigDecimal.valueOf(6)).build();
    	
        // Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.divisao(criarCalculadora);

    	// Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
        // Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(2));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(3));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(1));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isPositive();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Divisão");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(2));
    }
    
    @Test
    @DisplayName("Deve dividir com um número negativo.")
    public void deveDividirComUmNumeroNegativo(){
        // Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-18)).segundoValor(BigDecimal.valueOf(6)).build();

        // Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.divisao(criarCalculadora);

    	// Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
        // Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-3));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(-6), BigDecimal.valueOf(0));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-20));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isNegative();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Divisão");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-3));
    }
    
    @Test
    @DisplayName("Deve dividir com dois números negativos.")
    public void deveDividirComDoisNumerosNegativos(){
        // Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-18)).segundoValor(BigDecimal.valueOf(-6)).build();
    	
    	// Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.divisao(criarCalculadora);

    	// Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
        // Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(3));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(3));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(1));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isPositive();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Divisão");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(3));
    }
    
    @Test
    @DisplayName("Não deve dividir com segundo número com o valor de 0.")
    public void naoDeveDividirComSegundoNumeroComValorZero() {
    	// Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(20)).segundoValor(BigDecimal.valueOf(0)).build();
    	
    	// Execução
    	Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RegraException.class, () -> calculadoraServiceImpl.divisao(criarCalculadora));
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio, never()).save(any());
    	
        // Verificação se a instancia do erro foi o RegraException
     	assertThat(exception).isInstanceOf(RegraException.class).hasMessage("Não é possível dividir com o valor 0.");
    }
    
    @Test
    @DisplayName("Deve multiplicar com dois números positivos.")
    public void deveMultiplicarComNumerosPositivos(){
        // Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(5)).build();
    	
        // Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.multiplicar(criarCalculadora);

    	// Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
        // Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(50));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(40), BigDecimal.valueOf(60));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isPositive();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Multiplicação");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(50));
    }
    
    @Test
    @DisplayName("Deve multiplicar com dois números negativos.")
    public void deveMultiplicarComDoisNumerosNegativos(){
        // Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(-3)).build();
       	
        // Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.multiplicar(criarCalculadora);

    	// Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
        // Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(30));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isPositive();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Multiplicação");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(30));
    }
    
    @Test
    @DisplayName("Deve multiplicar com um número negativo.")
    public void deveMultiplicarComUmNumeroNegativo(){
        // Cenário
    	// Criar calculadora form
    	CalculadoraDTO criarCalculadora = CalculadoraDTO
    			.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(3)).build();
    	
    	// Execução
    	CalculadoraDTO calculadoraResultado = calculadoraServiceImpl.multiplicar(criarCalculadora);

    	// Capturando o valor total e o tipo de calculo
        ArgumentCaptor<Calculadora> captor = ArgumentCaptor.forClass(Calculadora.class);
        
        // Verificando se foi invocado o save 
        verify(calculadoraRepositorio).save(captor.capture());
    	
        // Verificação
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-30));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(-50), BigDecimal.valueOf(-20));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-550));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isNegative();
        
        Assertions.assertThat(calculadoraResultado.getPrimeiroValor()).isEqualTo(criarCalculadora.getPrimeiroValor());
        Assertions.assertThat(calculadoraResultado.getSegundoValor()).isEqualTo(criarCalculadora.getSegundoValor());
        Assertions.assertThat(calculadoraResultado.getTipoCalculo()).isEqualTo("Multiplicação");
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(-30));
    }
    
    @Test
    @DisplayName("Deve retornar lista de calculos realizados com sucesso.")
    public void deveRetornarListasDeCalculos() {
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
    	
    	// Mock para retornar a lista 
    	when(calculadoraRepositorio.findAll()).thenReturn(calculadoras);
    	
    	// Executando 
    	List<CalculadoraDTO> calculadorasRetorno = calculadoraServiceImpl.listagensCalculos();
    	
    	// Verificando
    	Assertions.assertThat(calculadorasRetorno.size()).isEqualTo(3);
    	Assertions.assertThat(!calculadorasRetorno.isEmpty()).isTrue();
    }

}
