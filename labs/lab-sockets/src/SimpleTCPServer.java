import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SimpleTCPServer {
    private ServerSocket serverSocket;
    private List<DataOutputStream> clients = new ArrayList<>();

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Servidor iniciado na porta " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Nova conexão de " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
            clients.add(clientOutput);

            Thread clientThread = new Thread(() -> {
                try {
                    DataInputStream input = new DataInputStream(clientSocket.getInputStream());
                    while (true) {
                        String message = input.readUTF();
                        System.out.println("Mensagem de " + clientSocket.getInetAddress() + ": " + message);
                        broadcast(message, clientOutput);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            clientThread.start();
        }
    }

    public synchronized void broadcast(String message, DataOutputStream sender) {
        for (DataOutputStream client : clients) {
            if (client != sender) {
                try {
                    client.writeUTF(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int serverPort = 6666;
        try {
            SimpleTCPServer server = new SimpleTCPServer();
            server.start(serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
