package models.socket;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.application.Platform;
import models.bill.Bill;
import models.checkInsOuts.CheckIn;
import models.dataBase.DaoHandler;
import models.exceptions.InvalidPasswordException;
import models.reservation.Reservation;
import models.room.*;
import models.service.Services;
import models.user.*;
import models.report.Report;
import models.reservation.Reservation;
import models.room.RoomType;
import models.user.Guest;
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

/**
 * This class represents a client handler that is used to handle incoming requests from clients.
 * It implements the Runnable interface to run the client handler in a separate thread.
 * It contains a socket that is used to communicate with the client, an input stream to read data from the client,
 * and an output stream to send data to the client.
 *
 * @see Request
 * @see Response
 * @see Client
 * @see Server
 * @see DaoHandler
 * @see UserData
 * @see ClientHandler
 * @see Runnable
 *
 * @author John, Arefe, Majid
 */
public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream out;

    /**
     * This constructor is used to create an instance of the ClientHandler class.
     *
     * @param socket the socket that is used to communicate with the client
     * @throws IOException if an I/O error occurs
     */
    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        out = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * This method is used to run the client handler in a separate thread.
     * It reads requests from the client, processes them, and sends responses back to the client.
     */
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
                    if (signupUser == null)
                        return new Response(Response.ResponseType.FAIL, "You don't have permission to create a new account!");

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
                } catch (InvalidPasswordException e) {
                    return new Response(Response.ResponseType.FAIL, e.getMessage());
                }
            }
            case UPDATE_INFO -> {
                try {
                    User newUser = handleEditInfo(request, dao);
                    return new Response(Response.ResponseType.SUCCESS, newUser);
                } catch (SQLException e) {
                    String message;
                    String errorMessage = e.getCause().getMessage();
                    if (errorMessage.contains("email"))
                        message = "This email is already taken by another user.";
                    else if (errorMessage.contains("phone"))
                        message = "This Phone number is already taken by another user.";
                    else
                        message = "Password is not strong enough";

                    return new Response(Response.ResponseType.FAIL, message);
                }
            }
            case DELETE_ACCOUNT -> {
                User deltedUser = null;
                try {
                    deltedUser = handleDeleteAccount(request, dao);
                    return new Response(Response.ResponseType.SUCCESS, deltedUser);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Response(Response.ResponseType.FAIL, "An unknown error acquired!");
                }

            }
            case PAY_BILL -> {
                try {
                    handlePayBill(request, dao);
                    return new Response(Response.ResponseType.SUCCESS, "done");
                } catch (SQLException e) {
                    return new Response(Response.ResponseType.FAIL, "An unknown error acquired!");
                }
            }
            case BOOK_ROOM -> {
                try {
                    Room room = handleBookRoom(request, dao);
                    return room != null ? new Response(Response.ResponseType.SUCCESS, room) :
                            new Response(Response.ResponseType.FAIL, "There isn't any available room with this information!");
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Response(Response.ResponseType.FAIL, "An unknown error acquired!");
                }
            }
            case REQUEST_SERVICE -> {
                try {
                    handleRequestService(request, dao);
                    return new Response(Response.ResponseType.SUCCESS, null);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return new Response(Response.ResponseType.FAIL, "An unknown error acquired!");
                }
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
                try {
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
            case ADD_REPORT -> {
                Report report = handeladdReport(request);
                return new Response(Response.ResponseType.SUCCESS, report);
            }
            case GET_ALL_REPORTS -> {
                List<Report> reports = handelGerAllReports(request);
                return new Response(Response.ResponseType.SUCCESS, reports);
            }
            case CHECK_IN -> {
                Guest guest = handleCheckIn(request);
                return guest != null ? new Response(Response.ResponseType.SUCCESS, guest) : new Response(Response.ResponseType.FAIL, null);
            }
            case CHECK_OUT -> {
                Guest guest = handleCheckOut(request);
                return guest != null ? new Response(Response.ResponseType.SUCCESS, guest) : new Response(Response.ResponseType.FAIL, null);

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
            List result = dao.search((Map) request.getData());
            if (result.isEmpty()) {
                return null;
            }
            return (User) result.getFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    // TODO: add admin and receptionist signup
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
                // newUser = new Admin((String) data.get("name"), (String) data.get("email"), (String) data.get("password"), (String) data.get("role"));
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
            String methodName = "set" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
            try {
                Method setter = user.getClass().getMethod(methodName, entry.getValue().getClass());
                setter.invoke(user, entry.getValue());
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        dao.update((T) user);
        UserData.getInstance().setUser(user);
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
    private <T> User handleDeleteAccount(Request request, DaoHandler<T> dao) throws SQLException {
        User user = request.getUser();
        dao.delete((T) user);

        return user;
    }

    // TODO: Advanced search with Date
    /**
     * This method is used to handle the book room request.
     * It searches for an available room with the given information and books it for the user.
     *
     * @param request the book room request
     * @param dao the DaoHandler to interact with the database
     * @return the room that is booked, or null if no room is available
     * @param <T> the type of the user
     *
     * @author John
     */
    private <T> Room handleBookRoom(Request request, DaoHandler<T> dao) throws SQLException {
        DaoHandler<Room> roomDao = new DaoHandler<>(Room.class);
        DaoHandler<Bill> billDaoHandler = new DaoHandler<>(Bill.class);
        DaoHandler<Reservation> reservationDaoHandler = new DaoHandler<>(Reservation.class);
        Map<String, Object> roomData = (Map) request.getData();
        Guest guest = (Guest) request.getUser();

        RoomType roomType = RoomType.valueOf(((String) roomData.get("type")).toUpperCase());
        LocalDate startDate = (LocalDate) roomData.get("startDate");
        int nights = Integer.parseInt((String) roomData.get("nights"));

        Map<String, Object> searchCriteria = new HashMap<>();
        searchCriteria.put("type", roomType);
        searchCriteria.put("status", Room.Status.AVAILABLE);
        List<Room> availableRooms = roomDao.search(searchCriteria);

        if (!availableRooms.isEmpty()) {
            LocalDate endDateLocal = startDate.plusDays(nights);
            Room room = availableRooms.getFirst();
            Reservation reservation = new Reservation(Date.from(startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), Date.from(endDateLocal.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()), room, guest);
            Bill bill = new Bill(room.getPrice() * nights);

            guest.setRoom(room);
            guest.setReservation(reservation);
            guest.setBill(bill);
            room.setStatus(Room.Status.BOOKED);

            roomDao.update(room);
            billDaoHandler.create(bill);
            reservationDaoHandler.create(reservation);
            dao.update((T) guest);
            UserData.getInstance().setUser(guest);
            return room;
        } else {
            return null;
        }
    }

    /**
     * This method is used to handle the request service request.
     * It adds the requested service to the user's bill.
     *
     * @param request the request service request
     * @param dao the DaoHandler to interact with the database
     * @param <T> the type of the user
     * @throws SQLException if an SQL error occurs
     *
     * @author John
     */
    private <T> void handleRequestService(Request request, DaoHandler<T> dao) throws SQLException {
        DaoHandler<Bill> billDaoHandler = new DaoHandler<>(Bill.class);

        Guest guest = (Guest) request.getUser();
        Services service = (Services) request.getData();

        Bill userBill = guest.getBill();
        userBill.increaseAdditionalServices(service.getPrice());
        billDaoHandler.update(userBill);
        dao.update((T) guest);
        UserData.getInstance().setUser(guest);

    }

    /**
     * This method is used to handle the pay bill request.
     * It marks the user's bill as paid and updates the user's information.
     *
     * @param request the pay bill request
     * @param dao the DaoHandler to interact with the database
     * @param <T> the type of the user
     * @throws SQLException if an SQL error occurs
     *
     * @author John
     */
    private <T> void handlePayBill(Request request, DaoHandler<T> dao) throws SQLException {
        Guest guest = (Guest) request.getUser();
        DaoHandler<Bill> billDaoHandler = new DaoHandler<>(Bill.class);
        Bill bill = guest.getBill();

        bill.pay();
        billDaoHandler.update(bill);
        dao.update((T) guest);
        UserData.getInstance().setUser(guest);
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
    public <T>Report handeladdReport(Request request) {
        DaoHandler<Report> reportDaoHandler = new DaoHandler<>(Report.class);
        Map<String, Object> data = (Map) request.getData();
        Report report = null;
        try {
            String Subject = (String) data.get("Subject");
            String Content = (String) data.get("Content");
            report = new Report(Subject, Content);
            reportDaoHandler.create(report);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return report;
    }
    public <T>List<Report> handelGerAllReports(Request request){
        DaoHandler<Report> reportDaoHandler = new DaoHandler<>(Report.class);
        try {
            return reportDaoHandler.getAll();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    private <T> Guest handleCheckIn(Request request) throws SQLException {
         DaoHandler<Guest> guestDaoHandler=new DaoHandler<>(Guest.class);
        List<T> result = guestDaoHandler.search((Map) request.getData());
        if (result.isEmpty()) {
            return null;
        }

        var receptionist = new Receptionist();
        receptionist.checkIn((Guest) result.getFirst());
        return (Guest) result.getFirst();
    }

    private <T> Guest handleCheckOut(Request request) throws SQLException {
        DaoHandler<Guest> guestDaoHandler=new DaoHandler<>(Guest.class);
        List<T> result = guestDaoHandler.search((Map) request.getData());
        if (result.isEmpty()) {
            return null;
        }

        var receptionist = new Receptionist();
        receptionist.checkOut((Guest) result.getFirst());
        return (Guest) result.getFirst();
    }
    
}
