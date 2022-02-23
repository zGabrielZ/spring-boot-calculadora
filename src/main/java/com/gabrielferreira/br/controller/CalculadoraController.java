package com.gabrielferreira.br.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabrielferreira.br.modelo.Calculadora;
import com.gabrielferreira.br.service.impl.CalculadoraServiceImpl;

@RestController
@RequestMapping("/calculadora")
public class CalculadoraController {

	@Autowired
	private CalculadoraServiceImpl calculadoraServiceImpl;
	
	@PostMapping("/somar")
	public ResponseEntity<Calculadora> calcularSoma(@RequestBody Calculadora calculadora){
		BigDecimal resultado = calculadoraServiceImpl.somar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor());
		calculadora.setValorTotal(resultado);
		return new ResponseEntity<>(calculadora,HttpStatus.CREATED);
	}
	
	@PostMapping("/subtrair")
	public ResponseEntity<Calculadora> calcularSubtracao(@RequestBody Calculadora calculadora){
		BigDecimal resultado = calculadoraServiceImpl.subtrair(calculadora.getPrimeiroValor(), calculadora.getSegundoValor());
		calculadora.setValorTotal(resultado);
		return new ResponseEntity<>(calculadora,HttpStatus.CREATED);
	}
	
	@PostMapping("/divisao")
	public ResponseEntity<Calculadora> calcularDivisao(@RequestBody Calculadora calculadora){
		BigDecimal resultado = calculadoraServiceImpl.divisao(calculadora.getPrimeiroValor(), calculadora.getSegundoValor());
		calculadora.setValorTotal(resultado);
		return new ResponseEntity<>(calculadora,HttpStatus.CREATED);
	}
	
	@PostMapping("/multiplicacao")
	public ResponseEntity<Calculadora> calcularMultiplicacao(@RequestBody Calculadora calculadora){
		BigDecimal resultado = calculadoraServiceImpl.multiplicar(calculadora.getPrimeiroValor(), calculadora.getSegundoValor());
		calculadora.setValorTotal(resultado);
		return new ResponseEntity<>(calculadora,HttpStatus.CREATED);
	}
	
}
