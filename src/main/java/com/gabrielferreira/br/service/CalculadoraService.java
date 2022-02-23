package com.gabrielferreira.br.service;

import java.math.BigDecimal;

public interface CalculadoraService {

	public BigDecimal somar(BigDecimal primeiroValor, BigDecimal segundoValor);
	
	public BigDecimal subtrair(BigDecimal primeiroValor, BigDecimal segundoValor);
	
	public BigDecimal divisao(BigDecimal primeiroValor, BigDecimal segundoValor);
	
	public BigDecimal multiplicar(BigDecimal primeiroValor, BigDecimal segundoValor);
}
