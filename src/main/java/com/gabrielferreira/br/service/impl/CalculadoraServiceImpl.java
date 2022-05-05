package com.gabrielferreira.br.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.modelo.Calculadora;
import com.gabrielferreira.br.repositorio.CalculadoraRepositorio;
import com.gabrielferreira.br.service.CalculadoraService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CalculadoraServiceImpl implements CalculadoraService{

	//	O compareTo retorna 3 valores:
	// -1: Quando vc compare seu numero com um valor maior
	//	0: quando vc compara seu numero com um numero igual
	//  1 : quando vc compara seu numero com um valor menor
	
	private final CalculadoraRepositorio calculadoraRepositorio;
	
	@Override
	public Calculadora somar(BigDecimal primeiroValor, BigDecimal segundoValor) {
		
		if(primeiroValor.compareTo(BigDecimal.ZERO) == -1 || segundoValor.compareTo(BigDecimal.ZERO) == -1) {
			throw new RegraException("Não deve somar com valores negativos.");
		}
		
		BigDecimal resultado = primeiroValor.add(segundoValor);
		
		Calculadora calculadora = getCalculadora(primeiroValor, segundoValor, resultado);
		calculadora.setTipoCalculo("Soma");
		
		return calculadoraRepositorio.save(calculadora);
	}

	@Override
	public Calculadora subtrair(BigDecimal primeiroValor, BigDecimal segundoValor) {
		BigDecimal resultado = primeiroValor.subtract(segundoValor);
		Calculadora calculadora = getCalculadora(primeiroValor, segundoValor, resultado);
		calculadora.setTipoCalculo("Subtração");
		return calculadoraRepositorio.save(calculadora);
	}

	@Override
	public Calculadora divisao(BigDecimal primeiroValor, BigDecimal segundoValor) {
		
		if(segundoValor.compareTo(BigDecimal.ZERO) == 0) {
			throw new RegraException("Não é possível dividir com o valor 0.");
		}
		
		BigDecimal resultado = primeiroValor.divide(segundoValor);
		Calculadora calculadora = getCalculadora(primeiroValor, segundoValor, resultado);
		calculadora.setTipoCalculo("Divisão");
		return calculadoraRepositorio.save(calculadora);
	}

	@Override
	public Calculadora multiplicar(BigDecimal primeiroValor, BigDecimal segundoValor) {
		BigDecimal resultado = primeiroValor.multiply(segundoValor);
		Calculadora calculadora = getCalculadora(primeiroValor, segundoValor, resultado);
		calculadora.setTipoCalculo("Multiplicação");
		return calculadoraRepositorio.save(calculadora);
	}
	
	private Calculadora getCalculadora(BigDecimal primeiroValor, BigDecimal segundoValor, BigDecimal resultado) {
		Calculadora calculadora = new Calculadora();
		calculadora.setPrimeiroValor(primeiroValor);
		calculadora.setSegundoValor(segundoValor);
		calculadora.setValorTotal(resultado);
		return calculadora;
	}

	@Override
	public List<Calculadora> listagensCalculos() {
		List<Calculadora> calculadoras = calculadoraRepositorio.findAll();
		return calculadoras;
	}

}
