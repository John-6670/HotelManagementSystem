package models.socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream out;

    public Client(String host, int port) throws IOException {
        socket = new    Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());

    }

    public void sendRequest(Request request) throws IOException {
        out.writeObject(request);
    }

    public Response receiveResponse() throws IOException, ClassNotFoundException {
        return (Response) input.readObject();
    }

    public void close() throws IOException {
        input.close();
        out.close();
        socket.close();
    }
}
