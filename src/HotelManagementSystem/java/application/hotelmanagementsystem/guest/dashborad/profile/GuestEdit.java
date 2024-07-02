package application.hotelmanagementsystem.guest.dashborad.profile;

import application.hotelmanagementsystem.CommonTasks;

import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.exceptions.InvalidPhoneNumberException;
import models.socket.Client;
import models.socket.Request;
import models.user.Guest;
import models.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GuestEdit implements Initializable {
    @FXML
    private TextField nationalId;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField password2;
    @FXML
    private PasswordField oldPass;

    public void edit() {
        Guest guest = (Guest) UserData.getInstance().getUser();
        String nationalID = this.nationalId.getText();
        String oldPassword = oldPass.getText();

        if (nationalID.equals(guest.getNationalId()) && oldPassword.equals(guest.getPassword())) {
            Client client = Main.client;
            try {
                Map<String, String> newData = new HashMap<>();
                if (!email.getText().isEmpty()) {
                    newData.put("email", email.getText());
                }

                if (!phoneNumber.getText().isEmpty()) {
                    newData.put("phoneNumber", phoneNumber.getText());
                }

                String password = this.password.getText();
                if (!password.isEmpty()) {
                    String repeatPassword = password2.getText();
                    if (password.equals(repeatPassword)) {
                        newData.put("password", password);
                    }
                }

                CommonTasks.showConfirmation("You are changing your profile information!!");
                client.sendRequest(new Request(Request.RequestType.UPDATE_INFO, guest, newData));
            } catch (IOException e) {
                CommonTasks.showError("An unknown error acquired");
                e.printStackTrace();
            }
        } else {
            CommonTasks.showError("Old password is incorrect.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(phoneNumber);
    }
}
