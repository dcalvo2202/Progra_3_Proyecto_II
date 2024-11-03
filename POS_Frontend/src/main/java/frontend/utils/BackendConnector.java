package frontend.utils;

import java.io.*;
import java.net.Socket;

public class BackendConnector {
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public BackendConnector(String host, int port) throws IOException {
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    // Método para enviar una solicitud al backend
    public String sendRequest(String request) throws IOException {
        writer.println(request);  // Enviar solicitud al backend
        return reader.readLine(); // Leer respuesta del backend
    }

    // Método para cerrar la conexión
    public void close() throws IOException {
        writer.close();
        reader.close();
        socket.close();
    }
}
