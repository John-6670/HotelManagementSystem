package models.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class represents a client that connects to the server.
 * It contains a socket that is used to communicate with the server.
 *
 * @see Server
 * @see Request
 * @see Response
 * @see ClientHandler
 *
 * @author John
 */
public class Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream out;

    /**
     * This constructor is used to create an instance of the Client class.
     *
     * @param host the host name of the server
     * @param port the port number of the server
     * @throws IOException if an I/O error occurs
     */
    public Client(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

    }

    /**
     * This method is used to send a request to the server.
     *
     * @param request the request that is sent to the server
     * @throws IOException if an I/O error occurs
     */
    public void sendRequest(Request request) throws IOException {
        out.writeObject(request);
    }

    /**
     * This method is used to receive a response from the server.
     *
     * @return the response that is received from the server
     * @throws IOException if an I/O error occurs
     * @throws ClassNotFoundException if the class of the object received from the server cannot be found
     */
    public Response receiveResponse() throws IOException, ClassNotFoundException {
        return (Response) input.readObject();
    }

    /**
     * This method is used to close the client.
     *
     * @throws IOException if an I/O error occurs
     */
    public void close() throws IOException {
        input.close();
        out.close();
        socket.close();
    }
}
