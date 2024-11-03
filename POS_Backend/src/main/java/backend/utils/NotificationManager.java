package backend.utils;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class NotificationManager {
    private static final List<PrintWriter> clients = new ArrayList<>();

    public static void addClient(PrintWriter client) {
        clients.add(client);
    }

    public static void removeClient(PrintWriter client) {
        clients.remove(client);
    }

    public static void notifyAllClients(String message) {
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }
}
