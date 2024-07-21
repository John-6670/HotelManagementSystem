package models;

import models.bill.Bill;
import models.checkInsOuts.CheckIn;
import models.checkInsOuts.CheckOut;
import models.dataBase.DaoHandler;
import models.reservation.Reservation;
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
import java.util.concurrent.TimeUnit;

public class TestModels {
    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {
        DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);
        Guest guest = guestDaoHandler.getById(2);
        DaoHandler<Reservation> reservationDaoHandler = new DaoHandler<>(Reservation.class);
        Reservation reservation = reservationDaoHandler.getById(1);
        guest.setReservation(reservation);
        DaoHandler<Room> roomDaoHandler = new DaoHandler<>(Room.class);
        Room room = roomDaoHandler.getById(101);
        room.setStatus(Room.Status.BOOKED);
        roomDaoHandler.update(room);
        guestDaoHandler.update(guest);
    }
}
