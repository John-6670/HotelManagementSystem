package models;

import application.hotelmanagementsystem.Main;
import models.bill.Bill;
import models.dataBase.DaoHandler;
import models.room.Room;
import models.room.RoomType;
import models.socket.Client;
import models.socket.Request;
import models.socket.Server;
import models.user.Guest;

import java.io.IOException;
import java.security.UnresolvedPermission;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        Server server = Server.getInstance(Main.port);
        new Thread(server::start).start();
        Client client = new Client(Main.address, Main.port);
        client.sendRequest(new Request(Request.RequestType.LOGIN, new Guest(), Map.of("username", "John", "password", "John/2005")));
        Guest guest = (Guest) client.receiveResponse().getData();
        client.sendRequest(new Request(Request.RequestType.UPDATE_INFO, guest, Map.of("email", "matin@gmail.com")));
    }
}
