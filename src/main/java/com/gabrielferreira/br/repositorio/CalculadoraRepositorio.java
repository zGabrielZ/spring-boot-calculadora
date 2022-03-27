package com.gabrielferreira.br.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gabrielferreira.br.modelo.Calculadora;

@Repository
public interface CalculadoraRepositorio extends JpaRepository<Calculadora, Long>{

}
