package models.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private int port = 8080;
    private ServerSocket server;
    private final List<ClientHandler> clients;

    public Server(int port) throws IOException {
        this.port = port;
        server = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    public void start() {
        while (true) {
            try {
                ClientHandler client = new ClientHandler(server.accept());
                acceptClient(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptClient(ClientHandler client) {
        clients.add(client);
        client.run();
    }
}
