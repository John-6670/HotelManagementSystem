package models.socket;

import application.hotelmanagementsystem.CommonTasks;
import models.checkInsOuts.CheckIn;
import models.dataBase.DaoHandler;
import models.user.Admin;
import models.user.Guest;
import models.user.Receptionist;
import models.user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
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


    private <T> Guest handleCheckIn(Request request, DaoHandler<T> dao) throws SQLException {
        List<T> result = dao.search((Map<String, Object>) request.getData());
        if (result.isEmpty()) {
            return null;
        }

        return (Guest) result.getFirst();
    }


    private final DaoHandler<Guest> guestDaoHandler=new DaoHandler<>(Guest.class);

    public Response checkInHandler(String nationalId, String name) throws SQLException {

        Map<String, Object> fieldValues = new HashMap<>();
        fieldValues.put("id", nationalId);
        fieldValues.put("name", name);


        List<Guest> guests = guestDaoHandler.search(fieldValues);

        if (guests.isEmpty()) {
            CommonTasks.showError("Guest Is Not Found! Name or NationalId is incorrect!");
            return new Response(Response.ResponseType.FAIL, "guest is not found");

        }
        else{
            Guest guest = guests.get(0);
            Receptionist receptionist = new Receptionist();

            receptionist.checkIn(guest);
            return new Response(Response.ResponseType.SUCCESS, "guest is found");
            }
        }

    private final DaoHandler<Guest> guestDaoHandler1=new DaoHandler<>(Guest.class);

    public Response checkOutHandler(String phoneNumber) throws SQLException {

        Map<String, Object> fieldValues = Map.of("phoneNumber", phoneNumber);


        List<Guest> guests = guestDaoHandler1.search(fieldValues);

        if (guests.isEmpty()) {
            CommonTasks.showError("Guest Is Not Found!Phone Number is incorrect!");
            return new Response(Response.ResponseType.FAIL, "guest not found");}
        else{
            Guest guest = guests.get(0);
            Receptionist receptionist = new Receptionist();

            receptionist.checkOut(guest);
            return new Response(Response.ResponseType.SUCCESS, "guest is found");
            }
    }






}
