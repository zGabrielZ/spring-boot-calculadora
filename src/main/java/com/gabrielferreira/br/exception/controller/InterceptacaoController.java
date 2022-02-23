package com.gabrielferreira.br.exception.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gabrielferreira.br.exception.RegraException;
import com.gabrielferreira.br.exception.modelo.Erro;

@ControllerAdvice
public class InterceptacaoController {

	
	@ExceptionHandler(RegraException.class)
	public ResponseEntity<Erro> regraException(RegraException e){
		Erro erro = new Erro(e.getMessage(), HttpStatus.BAD_REQUEST.value(), new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
