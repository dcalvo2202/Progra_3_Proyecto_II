package backend.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class BackendServer {
    private static final int PORT = 3306;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor backend escuchando en el puerto " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuevo cliente conectado");

                // Inicia un nuevo ClientHandler para cada conexión de cliente
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
