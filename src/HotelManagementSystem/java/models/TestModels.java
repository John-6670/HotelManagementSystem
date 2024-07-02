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
import org.sqlite.SQLiteException;

import java.io.IOException;
import java.security.UnresolvedPermission;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        DaoHandler<Guest> guestDao = new DaoHandler<>(Guest.class);
        Guest guest = new Guest("Ali", "Ali", "Ali/2005", "ali@gmail.com", "09381245167", "2005");
        guestDao.create(guest);
    }
}
