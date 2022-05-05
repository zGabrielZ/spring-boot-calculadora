package com.gabrielferreira.br.exception.modelo;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class Erro implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String mensagem;
	private Integer status;
	private String data;
	private List<String> listaErrosCampos;

}
