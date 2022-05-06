package com.gabrielferreira.br.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.modelo.Calculadora;
import com.gabrielferreira.br.modelo.dto.CalculadoraDTO;
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
	public CalculadoraDTO somar(CalculadoraDTO calculadoraDTO) {
		
		if(calculadoraDTO.getPrimeiroValor().compareTo(BigDecimal.ZERO) == -1 || calculadoraDTO.getSegundoValor().compareTo(BigDecimal.ZERO) == -1) {
			throw new RegraException("Não deve somar com valores negativos.");
		}
		
		// Populando o DTO
		BigDecimal resultado = calculadoraDTO.getPrimeiroValor().add(calculadoraDTO.getSegundoValor());
		calculadoraDTO.setValorTotal(resultado);
		calculadoraDTO.setTipoCalculo("Soma");
		
		// Populando a entidade e salvando
		Calculadora calculadora = getCalculadora(calculadoraDTO);
		calculadora = calculadoraRepositorio.save(calculadora);
		
		return new CalculadoraDTO(calculadora);
	}

	@Override
	public CalculadoraDTO subtrair(CalculadoraDTO calculadoraDTO) {
		// Populando o DTO
		BigDecimal resultado = calculadoraDTO.getPrimeiroValor().subtract(calculadoraDTO.getSegundoValor());
		calculadoraDTO.setValorTotal(resultado);
		calculadoraDTO.setTipoCalculo("Subtração");
		
		// Populando a entidade e salvando
		Calculadora calculadora = getCalculadora(calculadoraDTO);
		calculadora = calculadoraRepositorio.save(calculadora);
		
		return new CalculadoraDTO(calculadora);
	}

	@Override
	public CalculadoraDTO divisao(CalculadoraDTO calculadoraDTO) {
		
		if(calculadoraDTO.getSegundoValor().compareTo(BigDecimal.ZERO) == 0) {
			throw new RegraException("Não é possível dividir com o valor 0.");
		}
		
		// Populando DTO
		BigDecimal resultado = calculadoraDTO.getPrimeiroValor().divide(calculadoraDTO.getSegundoValor());
		calculadoraDTO.setValorTotal(resultado);
		calculadoraDTO.setTipoCalculo("Divisão");
		
		// Populando a entidade e salvando
		Calculadora calculadora = getCalculadora(calculadoraDTO);
		calculadora = calculadoraRepositorio.save(calculadora);
		
		return new CalculadoraDTO(calculadora);
	}

	@Override
	public CalculadoraDTO multiplicar(CalculadoraDTO calculadoraDTO) {
		// Populando DTO
		BigDecimal resultado = calculadoraDTO.getPrimeiroValor().multiply(calculadoraDTO.getSegundoValor());
		calculadoraDTO.setValorTotal(resultado);
		calculadoraDTO.setTipoCalculo("Multiplicação");
		
		// Populando a entidade e salvando
		Calculadora calculadora = getCalculadora(calculadoraDTO);
		calculadora = calculadoraRepositorio.save(calculadora);
		
		return new CalculadoraDTO(calculadora);
	}
	
	private Calculadora getCalculadora(CalculadoraDTO calculadoraDTO) {
		Calculadora calculadora = new Calculadora();
		calculadora.setPrimeiroValor(calculadoraDTO.getPrimeiroValor());
		calculadora.setSegundoValor(calculadoraDTO.getSegundoValor());
		calculadora.setValorTotal(calculadoraDTO.getValorTotal());
		calculadora.setTipoCalculo(calculadoraDTO.getTipoCalculo());
		return calculadora;
	}

	@Override
	public List<CalculadoraDTO> listagensCalculos() {
		List<Calculadora> calculadoras = calculadoraRepositorio.findAll();
		List<CalculadoraDTO> calculadoraDTOs = calculadoras.stream().map(c -> new CalculadoraDTO(c)).collect(Collectors.toList());
		return calculadoraDTOs;
	}

}
