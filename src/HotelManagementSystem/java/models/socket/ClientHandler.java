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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

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

    /**
     * This method is used to process a request from the client.
     * It handles different types of requests, such as login, signup, update info, delete account, book room, request service, and pay bill.
     * It uses a DaoHandler to interact with the database and perform the necessary operations.
     *
     * @param request the request to be processed
     * @return the response to be sent back to the client
     */
    private Response processRequest(Request request) throws SQLException {
        User user = request.getUser();
        DaoHandler<? extends User> dao = switch (user.getType()) {
            case GUEST -> new DaoHandler<>(Guest.class);
            case ADMIN -> new DaoHandler<>(Admin.class);
            default -> new DaoHandler<>(Receptionist.class);
        };

        switch (request.getType()) {
            case LOGIN -> {
                User loginUser = handeLogin(request, dao);
                return loginUser != null ? new Response(Response.ResponseType.SUCCESS, loginUser) : new Response(Response.ResponseType.FAIL, null);
            }
            case SIGNUP -> {
                try {
                    User signupUser = handleSignup(request, dao);
                    return new Response(Response.ResponseType.SUCCESS, signupUser);
                } catch (SQLException e) {
                    String message = e.getCause().getMessage();
                    String errorMessageIdentifier = switch (message) {
                        case String ignored when message.contains("username") -> "email";
                        case String ignored when message.contains("email") -> "email";
                        case String ignored when message.contains("nationalId") -> "national ID";
                        default -> "phone number";
                    };

                    String errorMessage = "This " + errorMessageIdentifier + " is already taken by another user.";
                    return new Response(Response.ResponseType.FAIL, errorMessage);
                }
            }
            case UPDATE_INFO -> {
                try {
                    User newUser = handleEditInfo(request, dao);
                    return new Response(Response.ResponseType.SUCCESS, newUser);
                } catch (SQLException e) {
                    String message;
                    if (e.getCause().getMessage().contains("email"))
                        message = "This email is already taken by another user.";
                    else
                        message = "This Phone number is already taken by another user.";

                    return new Response(Response.ResponseType.FAIL, message);
                }
            }
            case DELETE_ACCOUNT -> {
                User deltedUser = handleDeleteAccount(request, dao);
                return new Response(Response.ResponseType.SUCCESS, deltedUser);
            }
            case ADD_ROOM -> {
                try {
                    Room room = handelAddRoom(request);
                    return new Response(Response.ResponseType.SUCCESS, room);
                } catch (SQLException e) {
                    e.printStackTrace();

                    return new Response(Response.ResponseType.FAIL, "A room with this Room number exists!");
                }
            }
            case SEARCH_IF_ROOM_EXISTS -> {
                try{
                    Room room = handelSearchIfRoomExist(request);
                    return new Response(Response.ResponseType.SUCCESS, room);
                } catch (SQLException e) {
                    e.printStackTrace();

                    return new Response(Response.ResponseType.FAIL, "Couldn't find the room");
                }
            }
            case UPDATE_ROOM -> {
                Room room = handleUpdateRoomSit(request);
                return new Response(Response.ResponseType.SUCCESS, room);
            }
            case REMOVE_ROOM -> {
                Room room = handelRemoveRoom(request);
                return new Response(Response.ResponseType.SUCCESS, room);
            }
            case GET_ALL_ROOMS -> {
                List<Room> rooms = handelGetAllRooms(request);
                return new Response(Response.ResponseType.SUCCESS, rooms);
            }
            case GET_ADMIN_KEY -> {
                Admin admin = handelGetAdminKey(request);
                return new Response(Response.ResponseType.SUCCESS, admin);
            }
            case CHECK_If_PASS_IS_VALID -> {
                Admin admin = handelCheckPass(request);
                return new Response(Response.ResponseType.SUCCESS, admin);
            }
        }
        return null;
    }

    /**
     * This method is used to handle the login request.
     * It searches the database for a user with the given username and password.
     *
     * @param request the login request
     * @param dao the DaoHandler to interact with the database
     * @return the user that is logged in, or null if the login fails
     * @param <T> the type of the user
     *
     * @author John
     */
    private <T> User handeLogin(Request request, DaoHandler<T> dao) {
        try {
            List result = dao.search((Map)request.getData());
            if (result.isEmpty()) {
                return null;
            }
            return (User) result.getFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * This method is used to handle the signup request.
     * It creates a new user with the given information and adds it to the database.
     *
     * @param request the signup request
     * @param dao the DaoHandler to interact with the database
     * @return the new user that is created
     * @param <T> the type of the user
     * @throws SQLException if an SQL error occurs
     *
     * @author John
     */
    private <T> User handleSignup(Request request, DaoHandler<T> dao) throws SQLException {
        Map<String, Object> data = (Map) request.getData();
        User newUser = null;

        switch (request.getUser().getType()) {
            case GUEST:
                // Assuming Guest constructor takes specific parameters
                newUser = new Guest((String) data.get("name"), (String) data.get("username"), (String) data.get("password"), (String) data.get("email"), (String) data.get("phoneNumber"), (String) data.get("nationalId"));
                break;
            case ADMIN:
                // Assuming Admin constructor takes specific parameters
                String SecurityKey = handelSecurityKey();
                newUser = new Admin((String) data.get("name"),(String) data.get("username"),  (String) data.get("password"),(String) data.get("email"),  (String) data.get("phoneNumber"), (String) data.get("nationalId") , SecurityKey);
                break;
            default:
                Platform.runLater(() -> CommonTasks.showError("You don't have permission to create a new account."));
                break;
        }

        if (newUser != null) {
            dao.create((T) newUser);
        }

        return newUser;
    }

    /**
     * This method is used to handle the edit info request.
     * It updates the user's information with the given data.
     *
     * @param request the edit info request
     * @param dao the DaoHandler to interact with the database
     * @return the user with the updated information
     * @param <T> the type of the user
     * @throws SQLException if an SQL error occurs
     *
     * @author John
     */
    private <T> User handleEditInfo(Request request, DaoHandler<T> dao) throws SQLException {
        Map<String, Object> data = (Map) request.getData();
        User user = request.getUser();
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            String methodName = "set" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey ().substring(1);
            try {
                Method setter = user.getClass().getMethod(methodName, entry.getValue().getClass());
                setter.invoke(user, entry.getValue());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        dao.update((T) user);
        return user;
    }

    /**
     * This method is used to handle the delete account request.
     * It deletes the user's account from the database.
     *
     * @param request the delete account request
     * @param dao the DaoHandler to interact with the database
     * @return the user that is deleted
     * @param <T> the type of the user
     *
     * @author John
     */
    private <T> User handleDeleteAccount(Request request, DaoHandler<T> dao) {
        User user = request.getUser();
        try {
            dao.delete((T) user);
        } catch (SQLException e) {
            Platform.runLater(() -> CommonTasks.showError("An unknown error acquired."));
            e.printStackTrace();
        }

        return user;
    }

    private <T> Room handelAddRoom(Request request) throws SQLException {
        DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
        Map<String, Object> roomData = (Map) request.getData();

        int room_number = (int) roomData.get("room_number");
        RoomType type = (RoomType) roomData.get("type");
        Room room = new Room(room_number, type);

        roomDao.create(room);
        return room;
    }

    public <T> Room handelSearchIfRoomExist(Request request) throws SQLException{
        try {
            DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
            List result = roomDao.search((Map) request.getData());
            if (result.isEmpty()) {
                return null;
            }
            return (Room) result.getFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> Room handleUpdateRoomSit(Request request){
        DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
        Map<String, Object> roomData = (Map) request.getData();
        try {
            List result = roomDao.search(Map.of("room_number" , roomData.get("room_number")));
            if(!result.isEmpty()){
                Room room = (Room) result.getFirst();
                room.setStatus((Room.Status) roomData.get("status"));
                roomDao.update(room);
                return room;
            }
            else{
                return null;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    public <T> Room handelRemoveRoom(Request request){
        DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
        try {
            List result = roomDao.search((Map)request.getData());
            if(result.isEmpty()){
                return null;
            }
            else {
                Room room = (Room) result.getFirst();
                roomDao.delete(room);
                return room;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<Room> handelGetAllRooms(Request request) throws SQLException {
        DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
        List<Room> rooms = roomDao.getAll();
        Map<String, Object> roomData = (Map)request.getData();
        RoomType roomType = (RoomType) roomData.get("type");
        Room.Status status = (Room.Status) roomData.get("status");
        int min = (int)roomData.get("min");
        int max = (int)roomData.get("max");
        if(!rooms.isEmpty()){
            try {
                for(Room room : rooms){
                    if(
                            (!room.getType().equals(roomType)) && (roomType != null)
                                    ||     ((!room.getStatus().equals(status)) && (status != null))
                                    ||      ((room.getPrice() > max) && (max >= 0))
                                    ||      ((room.getPrice() < min) && (min >= 0))){
                        rooms.remove(room);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rooms;
    }

    public String handelSecurityKey() throws SQLException {
        DaoHandler<Admin> roomDao = new DaoHandler<>(Admin.class);
        int lowerBound = 10000;
        int upperBound = 99999;
        Random random = new Random();
        int code;
        List<Admin> admins = roomDao.getAll();
        code = random.nextInt(upperBound - lowerBound) + lowerBound;
        for(Admin admin : admins){
            if(Objects.equals(admin.getSecurityKey(), String.valueOf(code)))
                code = random.nextInt(upperBound - lowerBound) + lowerBound;

        }
        return String.valueOf(code);
    }
    public <T>Admin handelGetAdminKey (Request request) throws SQLException {
        DaoHandler<Admin> daoHandler = new DaoHandler<>(Admin.class);
        List result = daoHandler.search((Map)request.getData());
        if(!result.isEmpty()){
            Admin admin = (Admin)result.getFirst();
            return admin;
        }
        else
            return null;

    }
    public <T>Admin handelCheckPass(Request request) throws SQLException {
        DaoHandler<Admin> adminDaoHandler = new DaoHandler<>(Admin.class);
        List result = adminDaoHandler.search((Map)request.getData());
        if(!result.isEmpty()){
            Admin admin = (Admin)result.getFirst();
            return admin;
        }
        else
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