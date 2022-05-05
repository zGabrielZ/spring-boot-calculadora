package com.gabrielferreira.br.modelo;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "CALCULADORA")
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
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	
	@NotNull(message = "O primeiro valor não pode ser vazio.")
	@Column(name = "primeiro_valor")
	private BigDecimal primeiroValor;
	
	@NotNull(message = "O segundo valor não pode ser vazio.")
	@Column(name = "segundo_valor")
	private BigDecimal segundoValor;
	
	@Column(name = "valor_total")
	private BigDecimal valorTotal;
	
	@Column(name = "tipo_calculo")
	private String tipoCalculo;

}
