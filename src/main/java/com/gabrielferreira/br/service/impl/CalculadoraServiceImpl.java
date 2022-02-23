package com.gabrielferreira.br.service.impl;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.service.CalculadoraService;

@Service
public class CalculadoraServiceImpl implements CalculadoraService{

	//	O compareTo retorna 3 valores:
	// -1: Quando vc compare seu numero com um valor maior
	//	0: quando vc compara seu numero com um numero igual
	//  1 : quando vc compara seu numero com um valor menor
	
	@Override
	public BigDecimal somar(BigDecimal primeiroValor, BigDecimal segundoValor) {
		
		if(primeiroValor.compareTo(BigDecimal.ZERO) == -1 || segundoValor.compareTo(BigDecimal.ZERO) == -1) {
			throw new RegraException("Não deve somar com valores negativos.");
		}
		
		BigDecimal resultado = primeiroValor.add(segundoValor);
		return resultado;
	}

	@Override
	public BigDecimal subtrair(BigDecimal primeiroValor, BigDecimal segundoValor) {
		BigDecimal resultado = primeiroValor.subtract(segundoValor);
		return resultado;
	}

	@Override
	public BigDecimal divisao(BigDecimal primeiroValor, BigDecimal segundoValor) {
		
		if(segundoValor.compareTo(BigDecimal.ZERO) == 0) {
			throw new RegraException("Não é possível dividir com o valor 0.");
		}
		
		BigDecimal resultado = primeiroValor.divide(segundoValor);
		return resultado;
	}

	@Override
	public BigDecimal multiplicar(BigDecimal primeiroValor, BigDecimal segundoValor) {
		BigDecimal resultado = primeiroValor.multiply(segundoValor);
		return resultado;
	}

}
