# Calculadora
Foi utilizado com a linguagem Java com a versão 8 e o framework Spring Boot, arquitetura REST e também foi feito os testes unitários.

Para utilizar a aplicação é necessário utilizar o Postman.

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

id : Código da classe Calculadora

primeiroValor : Primeiro valor da classe Calculadora 

segundoValor : Segundo valor da classe Calculadora

#### Exemplo 

```
{
    "id":null,
    "primeiroValor":10,
    "segundoValor":20
}
```

##### Respostas
Created ! 201.
Bad Request ! 400.

##### Regras de negócios
Caso um dos valores for negativo quando for executar o endpoint 'calculadora/somar', é lançado uma exceção informando que não se deve somar com valores negativos.

Caso o segundo valor for menor que 0 quando for executar o endpoint 'calculadora/divisao', é lançado uma exceção informando que não se deve dividir com valores abaixo de 0.
