package com.gabrielferreira.br.modelo.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import com.gabrielferreira.br.modelo.Calculadora;

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
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CalculadoraDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull(message = "O primeiro valor não pode ser vazio.")
	private BigDecimal primeiroValor;
	
	@NotNull(message = "O segundo valor não pode ser vazio.")
	private BigDecimal segundoValor;
	
	private BigDecimal valorTotal;
	
	private String tipoCalculo;
	
	public CalculadoraDTO(Calculadora calculadora) {
		id = calculadora.getId();
		primeiroValor = calculadora.getPrimeiroValor();
		segundoValor = calculadora.getSegundoValor();
		valorTotal = calculadora.getValorTotal();
		tipoCalculo = calculadora.getTipoCalculo();
	}

}
