package models.socket;

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
        return (User) dao.search((Map) request.getData()).getFirst();
    }

    private <T> User handleSignup(Request request, DaoHandler<T> dao) {
        return null;
    }
}
