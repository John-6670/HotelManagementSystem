package models.socket;

import application.hotelmanagementsystem.CommonTasks;
import javafx.application.Platform;
import models.bill.Bill;
import models.dataBase.DaoHandler;
import models.service.Services;
import models.user.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private final ObjectInputStream input;
    private final ObjectOutputStream out;

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
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: implement processRequest method
    private Response processRequest(Request request) {
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
            } case REQUEST_SERVICE -> {
                try {
                    handleServiceRequest(request, dao);
                    return new Response(Response.ResponseType.SUCCESS, null);
                } catch (SQLException e) {
                    return new Response(Response.ResponseType.FAIL, "An unknown error acquired!");
                }

            }
        }
        return null;
    }

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
            default:
                Platform.runLater(() -> CommonTasks.showError("You don't have permission to create a new account."));
                break;
        }

        if (newUser != null) {
           dao.create((T) newUser);
        }

        return newUser;
    }

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
        return user;
    }

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

    private <T> void handleServiceRequest(Request request, DaoHandler<T> dao) throws SQLException {
        DaoHandler<Bill> billDaoHandler = new DaoHandler<>(Bill.class);
        Guest guest = (Guest) request.getUser();
        Services service = (Services) request.getData();
        Bill bill = guest.getBill();

        bill.increaseAdditionalServices(service.getPrice());
        dao.update((T) guest);
        billDaoHandler.update(bill);
    }
}
