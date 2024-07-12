package models;

import application.hotelmanagementsystem.Main;
import models.bill.Bill;
import models.dataBase.DaoHandler;
import models.reservation.Reservation;
import models.room.Room;
import models.room.RoomType;
import models.socket.Client;
import models.socket.Request;
import models.socket.Response;
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
        DaoHandler<Room> roomDaoHandler = new DaoHandler<>(Room.class);
        Room room = roomDaoHandler.getById(102);
        room.setStatus(Room.Status.AVAILABLE);
        roomDaoHandler.update(room);

//        DaoHandler<Bill> billDaoHandler = new DaoHandler<>(Bill.class);
//        Bill bill = billDaoHandler.getById(2);

        DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);
        Guest guest = guestDaoHandler.getById(4);
        guest.setRoom(null);
        guest.setReservation(null);
        guest.setBill(null);
        guestDaoHandler.update(guest);
    }
}
