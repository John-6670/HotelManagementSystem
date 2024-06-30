package models;

import models.socket.Client;
import models.socket.Request;
import models.socket.Server;
import models.user.Guest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Server server = new Server(8080);
        new Thread(server::start).start();
        Client client = new Client("localhost", 8080);
        client.sendRequest(new Request(Request.RequestType.LOGIN, new Guest(), Map.of("username", "John", "password", "John/2005")));
        Guest guest = (Guest) client.receiveResponse().getData();
        System.out.println(guest.getNationalId());
    }
}
