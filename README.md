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



