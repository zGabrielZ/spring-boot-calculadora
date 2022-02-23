package com.gabrielferreira.br.service;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.modelo.Calculadora;
import com.gabrielferreira.br.service.impl.CalculadoraServiceImpl;

@SpringBootTest
public class CalculadoraServiceTest {

	private CalculadoraServiceImpl calculadoraServiceImpl;
	
	// Utilizando o AssertJ -> https://joel-costigliola.github.io/assertj/assertj-core-quick-start.html
	
	@BeforeEach
	public void criarInstancias() {
		calculadoraServiceImpl = new CalculadoraServiceImpl();
	}
	
    @Test
    public void deveSomarComNumerosPositivos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(5)).build();

        // Execução
        BigDecimal resultado = calculadoraServiceImpl.somar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(15));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(20));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(resultado).isPositive();
    }
    
    @Test
    public void naoDeveSomarComUmNumeroNegativo() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(5)).build();

       try {
    	    // Execução
			calculadoraServiceImpl.somar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
			fail("Não deve lançar exception, pois tem valor negativo");
		} catch (RegraException e) {
			assertEquals("Não deve somar com valores negativos.", e.getMessage());
		}
    }
    
    @Test
    public void naoDeveSomarComDoisNumeroNegativo() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(-5)).build();

       try {
    	    // Execução
			calculadoraServiceImpl.somar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
			fail("Não deve lançar exception, pois tem valor negativo");
		} catch (RegraException e) {
			assertEquals("Não deve somar com valores negativos.", e.getMessage());
		}
    }
    
    @Test
    public void deveSubtrairComNumerosPositivos() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(20)).segundoValor(BigDecimal.valueOf(3)).build();
    	
    	// Execução
    	BigDecimal resultado = calculadoraServiceImpl.subtrair(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
    	
    	// Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(17));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(20));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(resultado).isPositive();
    }
    
    @Test
    public void deveSubtrairComUmNumeroNegativo() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-20)).segundoValor(BigDecimal.valueOf(10)).build();
    	
    	// Execução
    	BigDecimal resultado = calculadoraServiceImpl.subtrair(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
    	
    	// Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(-30));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(-40), BigDecimal.valueOf(-20));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(-50));
        Assertions.assertThat(resultado).isNegative();	
    }
    
    @Test
    public void deveSubtrairComDoisNumeroNegativo() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-20)).segundoValor(BigDecimal.valueOf(-10)).build();
    	
    	// Execução
    	BigDecimal resultado = calculadoraServiceImpl.subtrair(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
    	
    	// Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(-10));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(-30), BigDecimal.valueOf(-6));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(-12));
        Assertions.assertThat(resultado).isNegative();	
    }
    
    @Test
    public void deveDividirComNumerosPositivos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(12)).segundoValor(BigDecimal.valueOf(6)).build();

        // Execução
        BigDecimal resultado = calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(2));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(3));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(1));
        Assertions.assertThat(resultado).isPositive();
    }
    
    @Test
    public void deveDividirComUmNumeroNegativo(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-18)).segundoValor(BigDecimal.valueOf(6)).build();

        // Execução
        BigDecimal resultado = calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(-3));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(-6), BigDecimal.valueOf(0));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(-20));
        Assertions.assertThat(resultado).isNegative();
    }
    
    @Test
    public void deveDividirComDoisNumerosNegativos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-18)).segundoValor(BigDecimal.valueOf(-6)).build();

        // Execução
        BigDecimal resultado = calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(3));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(0), BigDecimal.valueOf(3));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(1));
        Assertions.assertThat(resultado).isPositive();
    }
    
    @Test
    public void naoDeveDividirComSegundoNumeroComValorZero() {
    	// Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(20)).segundoValor(BigDecimal.valueOf(0)).build();

       try {
    	    // Execução
			calculadoraServiceImpl.divisao(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());
			fail("Não deve lançar exception, pois o segundo valor é zero.");
		} catch (RegraException e) {
			assertEquals("Não é possível dividir com o valor 0.", e.getMessage());
		}
    }
    
    @Test
    public void deveMultiplicarComNumerosPositivos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(10)).segundoValor(BigDecimal.valueOf(5)).build();

        // Execução
        BigDecimal resultado = calculadoraServiceImpl.multiplicar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(50));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(40), BigDecimal.valueOf(60));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(resultado).isPositive();
    }
    
    @Test
    public void deveMultiplicarComDoisNumerosNegativos(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(-3)).build();

        // Execução
        BigDecimal resultado = calculadoraServiceImpl.multiplicar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(30));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(10));
        Assertions.assertThat(resultado).isPositive();
    }
    
    @Test
    public void deveMultiplicarComUmNumeroNegativo(){
        // Cenário
    	Calculadora calculadora1 = Calculadora.builder().id(null).primeiroValor(BigDecimal.valueOf(-10)).segundoValor(BigDecimal.valueOf(3)).build();

        // Execução
        BigDecimal resultado = calculadoraServiceImpl.multiplicar(calculadora1.getPrimeiroValor(), calculadora1.getSegundoValor());

        // Verificação
        Assertions.assertThat(resultado).isEqualTo(BigDecimal.valueOf(-30));
        Assertions.assertThat(resultado).isBetween(BigDecimal.valueOf(-50), BigDecimal.valueOf(-20));
        Assertions.assertThat(resultado).isGreaterThan(BigDecimal.valueOf(-550));
        Assertions.assertThat(resultado).isNegative();
    }

}
