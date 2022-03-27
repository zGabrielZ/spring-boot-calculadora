package com.gabrielferreira.br.service;

import java.math.BigDecimal;

import com.gabrielferreira.br.modelo.Calculadora;

public interface CalculadoraService {

	public Calculadora somar(BigDecimal primeiroValor, BigDecimal segundoValor);
	
	public Calculadora subtrair(BigDecimal primeiroValor, BigDecimal segundoValor);
	
	public Calculadora divisao(BigDecimal primeiroValor, BigDecimal segundoValor);
	
	public Calculadora multiplicar(BigDecimal primeiroValor, BigDecimal segundoValor);
}
