package com.gabrielferreira.br.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gabrielferreira.br.modelo.dto.CalculadoraDTO;
import com.gabrielferreira.br.service.impl.CalculadoraServiceImpl;

@RestController
@RequestMapping("/calculadora")
public class CalculadoraController {

	@Autowired
	private CalculadoraServiceImpl calculadoraServiceImpl;
	
	@PostMapping("/somar")
	public ResponseEntity<CalculadoraDTO> calcularSoma(@Valid @RequestBody CalculadoraDTO calculadoraDTO){
		calculadoraDTO = calculadoraServiceImpl.somar(calculadoraDTO);
		return new ResponseEntity<>(calculadoraDTO,HttpStatus.CREATED);
	}
	
	@PostMapping("/subtrair")
	public ResponseEntity<CalculadoraDTO> calcularSubtracao(@Valid @RequestBody CalculadoraDTO calculadoraDTO){
		calculadoraDTO = calculadoraServiceImpl.subtrair(calculadoraDTO);
		return new ResponseEntity<>(calculadoraDTO,HttpStatus.CREATED);
	}
	
	@PostMapping("/divisao")
	public ResponseEntity<CalculadoraDTO> calcularDivisao(@Valid @RequestBody CalculadoraDTO calculadoraDTO){
		calculadoraDTO = calculadoraServiceImpl.divisao(calculadoraDTO);
		return new ResponseEntity<>(calculadoraDTO,HttpStatus.CREATED);
	}
	
	@PostMapping("/multiplicacao")
	public ResponseEntity<CalculadoraDTO> calcularMultiplicacao(@Valid @RequestBody CalculadoraDTO calculadoraDTO){
		calculadoraDTO = calculadoraServiceImpl.multiplicar(calculadoraDTO);
		return new ResponseEntity<>(calculadoraDTO,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<CalculadoraDTO>> listagensCalculos(){
		List<CalculadoraDTO> calculadoras = calculadoraServiceImpl.listagensCalculos();
		return new ResponseEntity<>(calculadoras,HttpStatus.OK);
	}
	
}
