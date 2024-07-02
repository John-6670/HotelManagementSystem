package application.hotelmanagementsystem.guest.login;

import application.hotelmanagementsystem.*;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.socket.Client;
import models.socket.Request;
import models.user.Guest;
import models.user.User;

import java.io.IOException;
import java.util.Map;

public class GuestLogin extends CloseButton implements LoginController {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;

    @Override
    public void goToSignUpPage() {
        CommonTasks.pageNavigate("guest/login/guest-signUp-view.fxml");
    }

    @Override
    public void login() {
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.LOGIN, new Guest(), Map.of("username", username.getText(), "password", password.getText())));
            User user = (User) client.receiveResponse().getData();
            user.setClient(client);

            if (user != null) {
                UserData.getInstance().setUser(user);
                CommonTasks.pageNavigate("guest/dashboard/guest-dashboard.fxml");
            } else {
                CommonTasks.showError("Username or Password is incorrect");
            }
        } catch (IOException e) {
            CommonTasks.showError("Could not login at this moment. Please Try again in another time.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void back() {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }

    @Override
    public void forgetPass() {
        CommonTasks.pageNavigate("guest/login/forgot-pass-view.fxml");
    }
}
