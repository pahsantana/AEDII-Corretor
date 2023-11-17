# Aplicativo Java com Docker

Este é um exemplo de um aplicativo Java que utiliza Docker para execução dos servidores e clientes.

## Pré-requisitos

- [Docker](https://www.docker.com/products/docker-desktop) instalado na sua máquina.

## Instruções de Uso

1. **Construção da Imagem:**
   Execute o comando a seguir para construir a imagem Docker. Certifique-se de estar no diretório onde seus arquivos Java e texto estão localizados.

   ```bash
   docker build -t myjavaapp .

2. **Execução dos contêineres de memória secundária:**
    Execute os comandos a seguir para iniciar os contêineres de memória secundária para as faixas de letras A-I, J-R e S-Z.

    ```bash
    docker run -d -p 12345:12345 --name myserver_A_I myjavaapp
    docker run -d -p 12346:12345 --name myserver_J_R myjavaapp java Server J-R
    docker run -d -p 12347:12345 --name myserver_S_Z myjavaapp java Server S-Z
   ```
3. **Execução do contêiner de memória primária:**
    Execute os comandos a seguir para iniciar o contêiner de memória primária redirecionando conforme o input para as faixas de letras A-I, J-R e S-Z e as línguas portuguesa e espanhola

    ```bash
   docker run -it --rm --network host myjavaapp java Client
    ````

4. **Caso Docker não funcione, utilize a seguinte estrutura para rodar Java:**
   ```bash
      javac Server.java Client.java Trie.java
      java Server A-I
      java Client
   ````

## Resultados pt-br:

   Digite uma palavra (ou 'exit' para sair): aparo
   Conectado ao servidor na porta: 12345
   Escolha o idioma (pt-br ou es): pt-br
   Palavra correspondente do servidor: sapato
   Palavra correspondente do servidor: saro
   Palavra correspondente do servidor: separo
   Palavra correspondente do servidor: tapar
   Palavra correspondente do servidor: tapará
   Palavra correspondente do servidor: tapara
   Palavra correspondente do servidor: taparão
   Palavra correspondente do servidor: taparmo
   Palavra correspondente do servidor: tapado
   Palavra correspondente do servidor: tapamo
   Palavra correspondente do servidor: varo

   Cliente conectado
   Recebida a primeira letra da palavra: A
   Palavra recebida: aparo
   Idioma recebido: pt-br
   Palavras semelhantes encontradas: [sapato, saro, separo, tapar, tapará, tapara, taparão, taparmo, tapado, tapamo, varo]

## Resultados es:

   Digite uma palavra (ou 'exit' para sair): Hono
   Conectado ao servidor na porta: 12345
   Escolha o idioma (pt-br ou es): es
   Palavra correspondente do servidor: Soco
   Palavra correspondente do servidor: Sonso
   Palavra correspondente do servidor: Songo
   Palavra correspondente do servidor: Soto
   Palavra correspondente do servidor: Teno
   Palavra correspondente do servidor: Togo
   Palavra correspondente do servidor: Tona
   Palavra correspondente do servidor: Tondo
   Palavra correspondente do servidor: Torno
   Palavra correspondente do servidor: Toro
   Palavra correspondente do servidor: Uno
   Palavra correspondente do servidor: Uoro
   Palavra correspondente do servidor: Vino
   Palavra correspondente do servidor: Von
   Palavra correspondente do servidor: Zona

   Cliente conectado
   Recebida a primeira letra da palavra: H
   Palavra recebida: Hono
   Idioma recebido: es
   Palavras semelhantes encontradas: [Soco, Sonso, Songo, Soto, Teno, Togo, Tona, Tondo, Torno, Toro, Uno, Uoro, Vino, Von, Zona]

## Tecnologias utilizadas
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original-wordmark.svg" alt="Java" width="40" height="40"/><img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original-wordmark.svg" alt="Docker" width="40" height="40"/>



