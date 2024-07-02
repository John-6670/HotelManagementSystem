package models.socket;

import application.hotelmanagementsystem.CommonTasks;
import javafx.application.Platform;
import models.dataBase.DaoHandler;
import models.user.*;

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
            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: implement processRequest method
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
                User signupUser = handleSignup(request, dao);
                return new Response(Response.ResponseType.SUCCESS, signupUser);
            }
            case UPDATE_INFO -> {
                handleEditInfo(request, dao);
                return new Response(Response.ResponseType.SUCCESS, null);
            }
            case DELETE_ACCOUNT -> {
                User deltedUser = handleDeleteAccount(request, dao);
                return new Response(Response.ResponseType.SUCCESS, deltedUser);
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
            try {
                dao.create((T) newUser);
            } catch (SQLException e) {
                String message = e.getCause().getMessage();
                String errorMessageIdentifier = switch (message) {
                    case String ignored when message.contains("username") -> "email";
                    case String ignored when message.contains("email") -> "email";
                    case String ignored when message.contains("nationalId") -> "national ID";
                    default -> "phone number";
                };

                String errorMessage = "This " + errorMessageIdentifier + " is already taken by another user.";
                Platform.runLater(() -> CommonTasks.showError(errorMessage));
                
                e.printStackTrace();
                return null;
            }
        }

        return newUser;
    }

    private <T> void handleEditInfo(Request request, DaoHandler<T> dao) {
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

        try {
            dao.update((T) user);
        } catch (SQLException e) {
            if (e.getCause().getMessage().contains("email")) {
                Platform.runLater(() -> CommonTasks.showError("This email is already taken by another user."));
            } else {
                Platform.runLater(() -> CommonTasks.showError("This Phone number is already taken by another user."));
            }
        }
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
}
