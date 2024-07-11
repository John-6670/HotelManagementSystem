package models;

import models.bill.Bill;
import models.dataBase.DaoHandler;
import models.room.Room;
import models.room.RoomType;
import models.socket.Client;
import models.socket.Request;
import models.socket.Server;
import models.user.Guest;
import models.user.Receptionist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);



        DaoHandler<Receptionist> receptionistDaoHandler = new DaoHandler<>(Receptionist.class);
        var receptionist0 = new Receptionist();
        receptionistDaoHandler.create(receptionist0);
    }

}
