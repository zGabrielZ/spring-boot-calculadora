package com.gabrielferreira.br.service;
import java.util.List;
import com.gabrielferreira.br.modelo.dto.CalculadoraDTO;

public interface CalculadoraService {

	public CalculadoraDTO somar(CalculadoraDTO calculadoraDTO);
	
	public CalculadoraDTO subtrair(CalculadoraDTO calculadoraDTO);
	
	public CalculadoraDTO divisao(CalculadoraDTO calculadoraDTO);
	
	public CalculadoraDTO multiplicar(CalculadoraDTO calculadoraDTO);
	
	public List<CalculadoraDTO> listagensCalculos();
}
