package application.hotelmanagementsystem.guest.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.socket.Client;
import models.socket.ClientHandler;
import models.socket.Request;
import models.socket.Response;
import models.user.Guest;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GuestSignup extends CloseButton implements Initializable {
    @FXML
    private TextField nationalId;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField name;
    @FXML
    private TextField email;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;

    public void goToLoginPage() {
        CommonTasks.pageNavigate("guest/login/guest-login-view.fxml");
    }

    public void signUp() {
        if (CommonTasks.isAnyEmpty(nationalId, name, username, password, confirmPassword)) {
            CommonTasks.showError("Please fill all the required fields.");
            return;
        }
        if (!CommonTasks.isPasswordMatch(password, confirmPassword)) {
            CommonTasks.showError("Passwords don't match.");
            return;
        }
        try {
            Client client = Main.client;
            Map<String, String> data = new HashMap<>();
            data.put("name", name.getText());
            data.put("nationalId", nationalId.getText());
            data.put("username", username.getText());
            data.put("password", password.getText());

            if (!email.getText().isEmpty()) {
                data.put("email", email.getText());
            }
            if (!phoneNumber.getText().isEmpty()) {
                data.put("phoneNumber", phoneNumber.getText());
            }

            client.sendRequest(new Request(Request.RequestType.SIGNUP, new Guest(), data));
            Response response = client.receiveResponse();

            if (response.getResponseType() == Response.ResponseType.SUCCESS)
                goToLoginPage();
            else
                CommonTasks.showError((String) response.getData());

        } catch (IOException | ClassNotFoundException e) {
            CommonTasks.showError("Couldn't sign up now. Please try again later.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(phoneNumber);
    }
}
