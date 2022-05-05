package com.gabrielferreira.br.exception.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.exception.modelo.Erro;

@ControllerAdvice
public class InterceptacaoController {

	// Classe MethodArgumentNotValidException é chamada pois colocou a anotação @Valid nas requisição POST
	// Método para fazer a validação de anotação via hibernate e antes de fazer o fluxo com o código é encaminhado para este método
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Erro> handleValidationException(MethodArgumentNotValidException ex){
		// Obtem os erros de validação da anotação implementada na classe
		BindingResult bindingResult = ex.getBindingResult();
		
		// Instanciando uma lista para os campos e obtendo cada erro de validação
		List<String> campos = new ArrayList<String>();
		for(ObjectError objectError : bindingResult.getAllErrors()) {
			campos.add(objectError.getDefaultMessage());
		}
		
		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		
		Erro erro = new Erro("Campos inválidos", httpStatus.value(), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),campos);
		return new ResponseEntity<>(erro,httpStatus);
	}
	
	@ExceptionHandler(RegraException.class)
	public ResponseEntity<Erro> regraException(RegraException e){
		Erro erro = new Erro(e.getMessage(), HttpStatus.BAD_REQUEST.value(), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()),null);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
