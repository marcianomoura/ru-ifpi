# RUIFPI

## Objetivo Geral
O principal objetivo deste trabalho é desenvolver um software que contribua para a melhoria  qualitativa do serviço de alimentação do restaurante do IFPI, a partir da colaboração e participação dos usuários, que também possa promover  uma melhor conscientização alimentar a partir de informações nutricionais fornecidas  

## Objetivos Especificos
######1 - Construir um sistea web-responsivo
######2 - Construir uma API REST para integração futura com outras plataformas
######3 - Programar testes e vaidações para minimizar erros de software
######4 - Programar medidas de segurança para controle de acessso
######5 - Permitir uma melhor conscientização alimentar aos usuários

## Arquitetura do Sistema
Arquitetura Cliente/Servidor onde usuários fazem requisições e elas são processadas e respondidas pelo servidor. 

## Funcionalidades Principais
######1 - Avaliar Cardapios
######2 - Escolher Cardapios
######3 - Visualizar os cardápios do dia (Almoço e Janta)
######4 - Visualizar Cardapio da Semana
######5 - Obter informações Nutricionais sobre cada item do cardapio
######6 - Permitir identificar quais usuários são vegetariano
######7 - Relatórios gerais que permitam a administração saber o nível de satisfação dos cardápios oferecidos e quais os mais solicitados pelos usuários

## Tecnologia Utilizada
###### Linguagem - Java 1.8 com Vraptor 4.2 e JSP
###### SGBD - MySql 5.5.32
###### Framework de Estilização - Boostrap 3.3, JQuery 2.1.1
###### Servlet Container - Tomcat 8.0
###### Ferramenta de Modelagem - Astar Community 6.4
###### Ide de Desenvolvimento - Eclipse Mars (Release 4.5.1)


##Desenvolvimento
## Geral
######1 - Implementação do Padrão Session In View - Filtro q intercepta todas as requisições do cliente e garante que ao final do processamento dos dados uma resposta seja renderizada ao usuário.
######2 - Construção e Implementação de uma Interface DAO 
######3 - Controle de Acesso baseado no perfil do Usuario (Aluno/servidor_instituicao, Administração)

## Referente aos Usuários - Alunos/Servidores_Instituição
###Feito
######1 - Controle de acesso  - Realizado por meio de um Interceptor que valida cada tipo de usuário por meio de seu perfil restrigindo o acesso a algumas funcionalidades
######2 - Avaliações de Cardápio - Permite que o usuário faça avaliação dos cardápios. Foram implementadas registros e validações de avaliações que só podem ser registradas dentro de um intervalo de tempo e só pode ser feita uma única vez para um determinado prato
######3 - Cadastro e Autenticação de Usuário- Foram implementados o cadastro de usuário que só pode ser feito se o mesmo estiver com matricula ativa na instituição. Essa autorização é feita por meio de uma atualização semestral da base de dados que valida quais usuários permanecem com matriculas ativas na instituição. A autenticação é realizada após o cadastro ser efetivado com sucesso, o usuário fornecerá sua matricula e uma senha registrada no ato do cadastro.
######4 - Escolha de Cardápio - Foram implementadas métodos que permitem registrar e validar escolha de cardápio. Esta funcionalidade consiste em o aluno/servidor_da_instituicao escolher em um menu de cardápio ofertados, qual o cardápio de seu interesse para uma data pré-definida pela administração do refeitório.
######5 - Cardápio Semanal  - Essa funcionalidade consiste em fornecer os cardapios de toda a semana ou de uma outra data específica.

### A Fazer
######1 - Testes Unitários
######2 - API REST
######3 - Refatoração de Código - Refatorar Código baseado nos testes...

## Referente à Administração do Refeitório - Cordenação/Estagiária

### Feito
######1 - Controle de acesso  - Realizado por meio de um Interceptor que valida cada tipo de usuário por meio de seu perfil restrigindo o acesso a algumas funcionalidades.
######2 - Gerenciamento de Itens Alimentares  - Foram construidos metodos que permitem registrar,validar e alterar itens que compõem o cardápio. Cada item alimentar irá armazaenar informações nutricionais como: beneficios à saude, contra indicação, total de caloria - baseado na quantidade oferecida pelo refeitório.
######3 - Gerenciamento de Pratos - Foram construidos métodos que permite registrar, validar e alterar um prato. Este prato é chamado de prato pronto e irá compõe o menu de pratos prontos do refeitório. Este prato é composto por uma List de Itens e ao final teremos o total de calorias que este prato fornece.
######4 - Gerenciamento de Sobremesas  - Foram construidos metodos que permitem registrar,validar e alterar as sobremesas que irá ser componente do prato do dia.
######5 - Gerenciamento de Prato do Dia  - Foram construidos metodos que permitem registrar,validar e alterar o cardápio do dia. O prato do dia é composto por: PRATO PRONTO + SOBREMESA. Será permitido dois registros diários: um para a JANTA e outro para o ALMOÇO.
######6 - Gerenciamento de Instituição  - Foram construidos metodos que permitem registrar,validar e alterar a Instituição ao qual o Campus está vinculado.
######7 - Gerenciamento de Campus  - Foram construidos metodos que permitem registrar,validar e alterar o Campus e a Instituição a qual (IFPI) está vinculado.
######8 - Gerenciamento de Refeitório  - Foram construidos metodos que permitem registrar,validar e alterar o Refeitório e o Campus ao qual está Vinculado.
######9 - Relatório de Avaliação de Cardápio  - Foram construidos metodos que permitem analisar as avaliações de cardápios feitas por usuário.

### A Fazer
######1 - Relatório de Votação  - Construir métodos que permitem analisar o resultado da votação dos cardápios que foram disponibilizados para serem escolhidos pelos usuários.
######2 - Criação de Testes- Construir métodos de testes de Classe.
######3 - Refatoração de Código - Refatorar Código baseado nos testes...

















