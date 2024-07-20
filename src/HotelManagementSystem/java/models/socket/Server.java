package models.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a server that listens for incoming connections from clients.
 * It is a singleton class that is used to create a single instance of the server.
 * It contains a list of clients that are connected to the server.
 *
 * @see ClientHandler
 * @see Client
 *
 * @author John
 */
public class Server {
    private static Server server;
    private int port = 8080;
    private final ServerSocket serverSocket;
    private final List<ClientHandler> clients;

    /**
     * This constructor is used to create an instance of the Server class.
     *
     * @param port the port number that the server listens on
     * @throws IOException if an I/O error occurs
     */
    public Server(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
        clients = new ArrayList<>();
    }

    /**
     * This method is used to start the server.
     */
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

    /**
     * This method is used to stop the server.
     */
    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to accept a client connection.
     *
     * @param client the client to accept
     */
    public void acceptClient(ClientHandler client) {
        clients.add(client);
        client.run();
    }

    /**
     * This method is used to get the list of clients connected to the server.
     *
     * @param port the port number that the server listens on
     * @return server the server instance
     * @throws IOException if an I/O error occurs
     */
    public static synchronized Server getInstance(int port) throws IOException {
        if (server == null) {
            server = new Server(port);
        }

        return server;
    }

    /**
     * This method is used to get the list of clients connected to the server.
     *
     * @return clients the list of clients connected to the server
     */
    public int getPort() {
        return port;
    }
}
