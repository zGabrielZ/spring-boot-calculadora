# Calculadora

# Tecnologias
- Java (Versão 11)
- Spring Boot REST
- JUnit 
- Banco de dados H2 (Memória)
- Banco de dados PostgreSQL

# Endpoints da calculadora

### POST/calculadora/somar
Esse endpoint é responsável realizar uma soma.

### POST/calculadora/subtrair
Esse endpoint é responsável realizar uma subtração.

### POST/calculadora/divisao
Esse endpoint é responsável realizar a divisão.

### POST/calculadora/multiplicacao
Esse endpoint é responsável realizar a divisão.

#### Parametros 

primeiroValor : Primeiro valor da classe Calculadora 

segundoValor : Segundo valor da classe Calculadora

#### Exemplo 

```
{
    "primeiroValor":10,
    "segundoValor":20
}
```

##### Respostas
Created ! 201.

Bad Request ! 400.

### GET/calculadora/
Esse endpoint é responsável retornar lista de cálculos realizados.

#### Exemplo 

```
[
    {
        "id": 1,
        "primeiroValor": 10.00,
        "segundoValor": 40.00,
        "valorTotal": 50.00,
        "tipoCalculo": "Soma"
    },
    {
        "id": 2,
        "primeiroValor": 40.00,
        "segundoValor": 42.00,
        "valorTotal": -2.00,
        "tipoCalculo": "Subtração"
    }
]
```

##### Respostas
OK ! 200.

##### Regras de negócios
Caso um dos valores for negativo quando for executar o endpoint 'calculadora/somar', é lançado uma exceção informando que não se deve somar com valores negativos.

Caso o segundo valor for menor que 0 quando for executar o endpoint 'calculadora/divisao', é lançado uma exceção informando que não se deve dividir com valores abaixo de 0.
