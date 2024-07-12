package models.socket;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.application.Platform;
import models.bill.Bill;
import models.dataBase.DaoHandler;
import models.reservation.Reservation;
import models.room.Room;
import models.room.RoomType;
import models.user.Admin;
import models.user.Guest;
import models.user.Receptionist;
import models.user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream out;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Request request = (Request) input.readObject();
                Response response = processRequest(request);
                out.writeObject(response);
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: implement processRequest method
    private Response processRequest(Request request) throws SQLException {
        User user = request.getUser();
        DaoHandler<? extends User> dao = switch (user.getType()) {
            case GUEST -> new DaoHandler<Guest>(Guest.class);
            case ADMIN -> new DaoHandler<Admin>(Admin.class);
            default -> new DaoHandler<Receptionist>(Receptionist.class);
        };

        switch (request.getType()) {
            case LOGIN -> {
                User loginUser = handeLogin(request, dao);
                return loginUser != null ? new Response(Response.ResponseType.SUCCESS, loginUser) : new Response(Response.ResponseType.FAIL, null);
            }
            case SIGNUP -> {
                User signupUser = handleSignup(request, dao);
            }
            case ADD_ROOM -> {
                Room addedRoom = handelAddRoom(request, dao);
                return addedRoom != null ? new Response(Response.ResponseType.SUCCESS, addedRoom) : new Response(Response.ResponseType.FAIL, null);
            }
        }
        return null;
    }

    private <T> User handeLogin(Request request, DaoHandler<T> dao) throws SQLException {
        List result = dao.search((Map) request.getData());
        if (result.isEmpty()) {
            return null;
        }

        return (User) result.getFirst();
    }

    private <T> User handleSignup(Request request, DaoHandler<T> dao) {
        return null;
    }

    private <T> Room handelAddRoom(Request request, DaoHandler<T> dao) {
        DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
        Map<String, Object> roomData = (Map) request.getData();
        try {
            int room_number = (int) roomData.get("room_number");
            Map<String, Object> searchCriteria = new HashMap<>();
            searchCriteria.put("room_number", room_number);
            List<Room> list = roomDao.search(searchCriteria);

            if (list.isEmpty()) {
                Room room = new Room();
                room.setRoomNumber(room_number);
                room.setType(room.getType());
                roomDao.create(room);
                return room;
            } else {
                CommonTasks.showError("A room with this Room number exists!");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            CommonTasks.showError("An unknown error acquired!");
        }
        return null;
    }

//    private <T> Room handleBookRoom(Request request, DaoHandler<T> dao) {
//        DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
//        DaoHandler<Bill> billDaoHandler = new DaoHandler<>(Bill.class);
//        DaoHandler<Reservation> reservationDaoHandler = new DaoHandler<>(Reservation.class);
//        Map<String, Object> roomData = (Map) request.getData();
//        Guest guest = (Guest) request.getUser();
//
//        try {
//            RoomType roomType = RoomType.valueOf(((String) roomData.get("type")).toUpperCase());
//            LocalDate startDate = (LocalDate) roomData.get("startDate");
//            int nights = Integer.parseInt((String) roomData.get("nights"));
//
//            Map<String, Object> searchCriteria = new HashMap<>();
//            searchCriteria.put("type", roomType);
//            searchCriteria.put("status", Room.Status.AVAILABLE);
//            List<Room> availableRooms = roomDao.search(searchCriteria);
//
//            if (!availableRooms.isEmpty()) {
//                LocalDate endDateLocal = startDate.plusDays(nights);
//                Room room = availableRooms.getFirst();
//                Reservation reservation = new Reservation(Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), Date.from(endDateLocal.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), room, guest); // TODO: add reservation information
//                Bill bill = new Bill(room.getPrice() * nights);
//
//                guest.setRoom(room);
//                guest.setReservation(reservation);
//                guest.setBill(bill);
//                room.setStatus(Room.Status.BOOKED);
//
//                roomDao.update(room);
//                billDaoHandler.create(bill);
//                reservationDaoHandler.create(reservation);
//                dao.update((T) guest);
//                UserData.getInstance().setUser(guest);
//                return room;
//            } else {
//                Platform.runLater(() -> CommonTasks.showError("There isn't any available room with this information!"));
//                return null;
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            CommonTasks.showError("An unknown error acquired!");
//            return null;
//        }
//    }
}