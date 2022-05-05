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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.modelo.Calculadora;
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
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(5)).build();

    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor()).segundoValor(calculadora1.getSegundoValor())
    			.valorTotal(BigDecimal.valueOf(15)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
        // Execução
        Calculadora calculadoraResultado = calculadoraServiceImpl.somar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
        
        // Verificando o valor numérico
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isEqualTo(BigDecimal.valueOf(15));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(20));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadoraResultado.getValorTotal()).isPositive();
    }
    
    @Test
    @DisplayName("Não deve somar com um número negativo pois está implementado na regra de negócio.")
    public void naoDeveSomarComUmNumeroNegativo() {
    	// Cenário
    	BigDecimal primeiroValor = BigDecimal.valueOf(-10);
    	BigDecimal segundoValor = BigDecimal.valueOf(10);
    	
    	
    	// Execução 
    	Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RegraException.class, () -> calculadoraServiceImpl.somar(primeiroValor
    			, segundoValor));
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio, never()).save(any());
        
        // Verificação se a instancia do erro foi o RegraException
     	assertThat(exception).isInstanceOf(RegraException.class).hasMessage(exception.getMessage());
        
    }
    
    @Test
    @DisplayName("Não deve somar com dois número negativos pois está implementado na regra de negócio.")
    public void naoDeveSomarComDoisNumeroNegativo() {
    	// Cenário
    	BigDecimal primeiroValor = BigDecimal.valueOf(-10);
    	BigDecimal segundoValor = BigDecimal.valueOf(-10);
    	
    	
    	// Execução 
    	Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RegraException.class, () -> calculadoraServiceImpl.somar(primeiroValor
    			, segundoValor));
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio, never()).save(any());
        
        // Verificação se a instancia do erro foi o RegraException
     	assertThat(exception).isInstanceOf(RegraException.class).hasMessage(exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve subtrair os dois números positivos.")
    public void deveSubtrairComNumerosPositivos() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(20)).segundoValor(BigDecimal.valueOf(3)).build();
    	
    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(17)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
    	// Execução
    	Calculadora calculadora = calculadoraServiceImpl.subtrair(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
    	// Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(17));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(20));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadora.getValorTotal()).isPositive();
    }
    
    @Test
    @DisplayName("Deve subtrair com um número negativo.")
    public void deveSubtrairComUmNumeroNegativo() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-20)).segundoValor(BigDecimal.valueOf(10)).build();
    	
    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(-30)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
    	// Execução
    	Calculadora calculadora = calculadoraServiceImpl.subtrair(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
    	// Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(-30));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(-40), BigDecimal.valueOf(-20));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-50));
        Assertions.assertThat(calculadora.getValorTotal()).isNegative();	
    }
    
    @Test
    @DisplayName("Deve subtrair com dois número negativos.")
    public void deveSubtrairComDoisNumeroNegativo() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-20)).segundoValor(BigDecimal.valueOf(-10)).build();
    	
    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(-10)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
    	// Execução
    	Calculadora calculadora = calculadoraServiceImpl.subtrair(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
    	// Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(-10));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(-30), BigDecimal.valueOf(-6));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-12));
        Assertions.assertThat(calculadora.getValorTotal()).isNegative();	
    }
    
    @Test
    @DisplayName("Deve dividir com dois números positivos.")
    public void deveDividirComNumerosPositivos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(12)).segundoValor(BigDecimal.valueOf(6)).build();

    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(2)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
        // Execução
    	Calculadora calculadora = calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
        // Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(2));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(3));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(1));
        Assertions.assertThat(calculadora.getValorTotal()).isPositive();
    }
    
    @Test
    @DisplayName("Deve dividir com um número negativo.")
    public void deveDividirComUmNumeroNegativo(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-18)).segundoValor(BigDecimal.valueOf(6)).build();

    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(-3)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
        // Execução
    	Calculadora calculadora = calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
        // Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(-3));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(-6), BigDecimal.valueOf(0));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-20));
        Assertions.assertThat(calculadora.getValorTotal()).isNegative();
    }
    
    @Test
    @DisplayName("Deve dividir com dois números negativos.")
    public void deveDividirComDoisNumerosNegativos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-18)).segundoValor(BigDecimal.valueOf(-6)).build();

    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(3)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
        // Execução
    	Calculadora calculadora = calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
        // Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(3));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(3));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(1));
        Assertions.assertThat(calculadora.getValorTotal()).isPositive();
    }
    
    @Test
    @DisplayName("Não deve dividir com segundo número com o valor de 0.")
    public void naoDeveDividirComSegundoNumeroComValorZero() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(20)).segundoValor(BigDecimal.valueOf(0)).build();
    	
    	// Execução
    	Throwable exception = org.junit.jupiter.api.Assertions.assertThrows(RegraException.class, () -> calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor()
    			, calculadora1.getSegundoValor()));
    	
    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio, never()).save(any());
    	
        // Verificação se a instancia do erro foi o RegraException
     	assertThat(exception).isInstanceOf(RegraException.class).hasMessage(exception.getMessage());
    }
    
    @Test
    @DisplayName("Deve multiplicar com dois números positivos.")
    public void deveMultiplicarComNumerosPositivos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(5)).build();

    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(50)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
        // Execução
    	Calculadora calculadora = calculadoraServiceImpl.multiplicar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
        // Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(50));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(40), BigDecimal.valueOf(60));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadora.getValorTotal()).isPositive();
    }
    
    @Test
    @DisplayName("Deve multiplicar com dois números negativos.")
    public void deveMultiplicarComDoisNumerosNegativos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(-3)).build();

    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(30)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
        // Execução
    	Calculadora calculadora = calculadoraServiceImpl.multiplicar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
        // Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(30));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(calculadora.getValorTotal()).isPositive();
    }
    
    @Test
    @DisplayName("Deve multiplicar com um número negativo.")
    public void deveMultiplicarComUmNumeroNegativo(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(3)).build();

    	// Resultado do mock
    	Calculadora calculadoraRetorno = Calculadora.builder().id(1L).primeiroValor(calculadora1.getPrimeiroValor())
    			.segundoValor(calculadora1.getSegundoValor()).valorTotal(BigDecimal.valueOf(-30)).build();
    	when(calculadoraRepositorio.save(any())).thenReturn(calculadoraRetorno);
    	
        // Execução
    	Calculadora calculadora = calculadoraServiceImpl.multiplicar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

    	// Verificação se foi invocado o save
        verify(calculadoraRepositorio).save(any());
    	
        // Verificação
        Assertions.assertThat(calculadora.getValorTotal()).isEqualTo(BigDecimal.valueOf(-30));
        Assertions.assertThat(calculadora.getValorTotal()).isBetween(BigDecimal.valueOf(-50), BigDecimal.valueOf(-20));
        Assertions.assertThat(calculadora.getValorTotal()).isGreaterThan(BigDecimal.valueOf(-550));
        Assertions.assertThat(calculadora.getValorTotal()).isNegative();
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
    	List<Calculadora> calculadorasRetorno = calculadoraServiceImpl.listagensCalculos();
    	
    	// Verificando
    	Assertions.assertThat(calculadorasRetorno.size()).isEqualTo(3);
    	Assertions.assertThat(!calculadorasRetorno.isEmpty()).isTrue();
    }

}
