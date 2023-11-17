# Use a imagem base do OpenJDK com as ferramentas de desenvolvimento
FROM openjdk:11

# Copie os arquivos necessários para o contêiner
COPY Server.java /app/Server.java
COPY Client.java /app/Client.java
COPY Trie.java /app/Trie.java
COPY A-I_pt-br.txt /app/A-I_pt-br.txt
COPY A-I_es.txt /app/A-I_es.txt
COPY J-R_pt-br.txt /app/J-R_pt-br.txt
COPY J-R_es.txt /app/J-R_es.txt
COPY S-Z_pt-br.txt /app/S-Z_pt-br.txt
COPY S-Z_es.txt /app/S-Z_es.txt

# Defina o diretório de trabalho
WORKDIR /app

# Compile o código Java
RUN javac Server.java Client.java Trie.java

# Exponha a porta utilizada pelo servidor
EXPOSE 12345

# Comando padrão para iniciar o servidor (ajuste conforme necessário)
CMD ["java", "Server", "A-I"]
