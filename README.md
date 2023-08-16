# Bate-papo em linha de comando usando Sockets TCP

Este é um projeto simples de bate-papo em linha de comando implementado em Java, utilizando sockets TCP para comunicação entre um cliente e um servidor. O projeto permite que vários clientes se conectem ao servidor e troquem mensagens em um ambiente de bate-papo em grupo.

## Funcionamento

### Cliente (SimpleTCPClient.java)

O cliente possui duas threads: uma para enviar mensagens para o servidor e outra para receber mensagens do servidor. As mensagens digitadas pelo usuário são lidas da entrada padrão (teclado) e enviadas para o servidor. As mensagens recebidas do servidor são exibidas no console.

### Servidor (SimpleTCPServer.java)

O servidor aceita conexões de clientes em um loop infinito. Cada cliente que se conecta é tratado em uma thread separada. As mensagens enviadas por um cliente são enviadas a todos os outros clientes conectados, criando um ambiente de bate-papo em grupo.

## Como Usar

1. Compile os arquivos Java:

   ```sh
   javac SimpleTCPClient.java
   javac SimpleTCPServer.java
    ```
2. Execute o servidor em um terminal:

    ```sh
    java SimpleTCPServer
    ```
3. Execute o cliente em um ou mais terminais diferentes:

    ```sh
    java SimpleTCPClient
    ```
