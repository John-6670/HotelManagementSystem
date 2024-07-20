package application.hotelmanagementsystem.guest.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.dataBase.DaoHandler;
import models.socket.Client;
import models.socket.Request;
import models.socket.Response;
import models.user.Guest;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class GuestForgetPassword extends CloseButton {
    @FXML
    private TextField nationalID;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField repeatPassword;

    public void back() {
        CommonTasks.pageNavigate("guest/login/guest-login-view.fxml");
    }

    public void changePass() {
        DaoHandler<Guest> guestDaoHandler = new DaoHandler<>(Guest.class);
        String nationalId = nationalID.getText();
        String username = this.username.getText();
        try {
            List<Guest> result = guestDaoHandler.search(Map.of("username", username));
            if (result.isEmpty()) {
                CommonTasks.showError("No user found");
                return;
            }

            Guest guest = result.getFirst();
            if (!guest.getNationalId().equals(nationalId)) {
                CommonTasks.showError("Username or national ID is incorrect");
                return;
            }

            String password = this.password.getText();
            String repeatPassword = this.repeatPassword.getText();
            if (!password.equals(repeatPassword)) {
                CommonTasks.showError("Passwords does not match");
            }

            try {
                var client = new Client(Main.address, Main.port);
                client.sendRequest(new Request(Request.RequestType.UPDATE_INFO, guest, Map.of("password", password)));
                Response response = client.receiveResponse();
                if (response.getResponseType() == Response.ResponseType.FAIL) {
                    CommonTasks.showError((String) response.getData());
                }
            } catch (IOException | ClassNotFoundException e) {
                CommonTasks.showError("An unknown error acquired!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
