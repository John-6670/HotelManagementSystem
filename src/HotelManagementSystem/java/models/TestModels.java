package models;

import models.bill.Bill;
import models.dataBase.DaoHandler;
import models.report.Report;
import models.room.Room;
import models.room.RoomType;
import models.socket.Client;
import models.socket.Request;
import models.socket.Server;
import models.user.Admin;
import models.user.Guest;
import models.user.Receptionist;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
          DaoHandler<Receptionist> receptionistDaoHandler = new DaoHandler<>(Receptionist.class);
          Receptionist receptionist = new Receptionist("Sara", "Sara", "Sara/2005", "sara@gmail.com", "09671452678", "1765", 24, null, new Date());
          receptionistDaoHandler.create(receptionist);
    }
}
