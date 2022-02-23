package com.gabrielferreira.br.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Calculadora implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	private Long id;
	
	private BigDecimal primeiroValor;
	private BigDecimal segundoValor;
	private BigDecimal valorTotal;

}
