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

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
          DaoHandler<Report> daoHandler = new DaoHandler<>(Report.class);
          Report report = new Report("Test" , "Test");
          daoHandler.create(report);
    }
}
