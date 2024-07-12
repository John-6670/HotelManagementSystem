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
       /* DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);
        DaoHandler<Room> roomDaoHandler = new DaoHandler<>(Room.class);*/
        /*var room = new Room(23 , RoomType.SINGLE);
        var guest = new Guest("ali" , "12345" , "09127894507");
        guestDaoHandler.create(guest);
        roomDaoHandler.create(room);
        Date startDate = new Date(); // Replace with actual start date
        Date endDate = new Date(startDate.getTime() + TimeUnit.DAYS.toMillis(3));



        DaoHandler<Reservation> reservationDaoHandler = new DaoHandler<>(Reservation.class);
        var reservation = new Reservation(22 , startDate , endDate , room , guest);
        reservationDaoHandler.create(reservation);*/

        /*DaoHandler<Receptionist> receptionistDaoHandler = new DaoHandler<>(Receptionist.class);

        Receptionist receptionist = new Receptionist("Arefe Yeganepour", "a.yeganepour", "@1384Arefe1384", "arefe.yegane1399@gmail.com", "09944030697", "0200511947", 5000.0, new Date(), new Date());
        receptionistDaoHandler.create(receptionist);*/

        /*DaoHandler<CheckIn> checkInDaoHandler = new DaoHandler<>(CheckIn.class);
        DaoHandler<Receptionist> receptionistDaoHandler = new DaoHandler<>(Receptionist.class);
        DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);
        DaoHandler<Room> roomDaoHandler = new DaoHandler<>(Room.class);

        Room room = new Room(20, RoomType.SINGLE);
        Guest guest = new Guest("Ali", "67899", "09194562323");

        // Creating the guest and room entries
        guestDaoHandler.create(guest);
        roomDaoHandler.create(room);

        // Creating the receptionist entry
        /*Receptionist receptionist = new Receptionist("Arefe Yeganepour", "a.yeganepour", "@1384Arefe1384",
                "arefe.yegane1399@gmail.com", "09944030692",
                "0200511947", 5000.0, new Date(), new Date());
        receptionistDaoHandler.create(receptionist);

        // Creating the check-in entry
        CheckIn checkin = new CheckIn(room, guest, receptionist);
        checkInDaoHandler.create(checkin);*/

        DaoHandler<CheckOut> checkOutDaoHandler = new DaoHandler<>(CheckOut.class);
        DaoHandler<Receptionist> receptionistDaoHandler = new DaoHandler<>(Receptionist.class);
        DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);
        DaoHandler<Room> roomDaoHandler = new DaoHandler<>(Room.class);

        Room room = roomDaoHandler.getById(101);
        Guest guest = guestDaoHandler.getById(8);
        Receptionist receptionist = receptionistDaoHandler.getById(1);

        // Creating the check-in entry
        var checkout = new CheckOut(room, guest, receptionist);
        checkOutDaoHandler.create(checkout);
    }
}
