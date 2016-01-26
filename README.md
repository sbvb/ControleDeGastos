# Controle de gastos Android
# 2015/2
Projeto de gerenciamento de gastos em android


Requisitos funcionais:
- Sistema baseado em SOA,  utilizando o java com axis2 para webservice, usando mysql como banco de dados.
- Um mês possui vários custos
- Cada custo possui uma modalidade ( ex: luz, água, etc..)
- Modalidades adicionadas pelo usuário vão para cloud, e outros usuários poderão utilizá-las
- Funcionalidades do usuário
 - O usuário pode adicionar uma modalidade.
 - O usuário pode cadastrar os custos de cada mês
 - O usuário pode alterar os custos de cada mês
 - O usuário pode cadastrar-se para usar os serviços da cloud.
 - O usuário pode sincronizar os seus custos diretamente na cloud ( backup )
- Depois de um certo número de meses, o usuário terá uma previsão do custo que terá para o mês seguinte.
- Plotar um gráfico dos custos x tempo
- Futuramente uma aplicação web. 

Modelo do Banco de dados:

![screenshot at 2016-01-25 23 54 32](https://cloud.githubusercontent.com/assets/8482579/12570483/1bb71526-c3bf-11e5-829f-7a22315b8932.png)

Requisitos não funcionais:
 - A sincronização pode ser feita apenas aplicando a diferença das operações, diminuindo o consumo de banda.
