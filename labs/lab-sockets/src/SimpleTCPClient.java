import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class SimpleTCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public void start(String serverIp, int serverPort) throws IOException {
        System.out.println("Conectando ao servidor " + serverIp + ":" + serverPort);
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        Thread sendThread = new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String message;
                while ((message = reader.readLine()) != null) {
                    output.writeUTF(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread receiveThread = new Thread(() -> {
            try {
                while (true) {
                    String message = input.readUTF();
                    System.out.println("Servidor: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sendThread.start();
        receiveThread.start();
    }

    public static void main(String[] args) {
        String serverIp = "127.0.0.1";
        int serverPort = 6666;
        try {
            SimpleTCPClient client = new SimpleTCPClient();
            client.start(serverIp, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
