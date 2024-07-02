package models.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static Server server;
    private int port = 8080;
    private final ServerSocket serverSocket;
    private final List<ClientHandler> clients;

    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    public void start() {
        while (true) {
            try {
                ClientHandler client = new ClientHandler(serverSocket.accept());
                acceptClient(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptClient(ClientHandler client) {
        clients.add(client);
        client.run();
    }

    public static synchronized Server getInstance(int port) throws IOException {
        if (server == null) {
            server = new Server(port);
        }

        return server;
    }

    public int getPort() {
        return port;
    }
}
