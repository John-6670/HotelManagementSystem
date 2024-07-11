package models;

import models.bill.Bill;
import models.dataBase.DaoHandler;
import models.room.Room;
import models.room.RoomType;
import models.socket.Client;
import models.socket.Request;
import models.socket.Server;
import models.user.Admin;
import models.user.Guest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);
        DaoHandler<Admin>  adminDaoHandler = new DaoHandler<>(Admin.class);
        Guest guest = new Guest("Joe", "Joe_99", "Joe/2005", null, null, "1365");
        guestDaoHandler.create(guest);
        Admin admin = new Admin("Majid" , "Majid9091" , "majid9091akoo" , null , null , "15524");
        adminDaoHandler.create(admin);
    }
}
