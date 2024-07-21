package application.hotelmanagementsystem.admin.dashboard.profile;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.socket.Client;
import models.socket.Request;
import models.socket.Response;
import models.user.Admin;
import models.user.Guest;
import models.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminEdit implements Initializable {
    @FXML
    private TextField nationalId;
    @FXML
    private PasswordField oldPassword;
    @FXML
    private TextField newEmail;
    @FXML
    private TextField newPhoneNumber;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField newRepPassword;


    public void edit() throws IOException {
        Admin admin = (Admin)UserData.getInstance().getUser();
        String nationalID = this.nationalId.getText();

        if (nationalID.equals(admin.getNationalId()) && oldPassword.getText().equals(admin.getPassword())) {
            try {
                Map<String, String> newData = new HashMap<>();
                if (!newEmail.getText().isEmpty()) {
                    newData.put("email", newEmail.getText());
                }

                if (!newPhoneNumber.getText().isEmpty()) {
                    newData.put("phoneNumber", newPhoneNumber.getText());
                }

                String password = this.newPassword.getText();
                if (!password.isEmpty()) {
                    String repeatPassword = newRepPassword.getText();
                    if (password.equals(repeatPassword)) {
                        newData.put("password", password);
                    }
                }

                CommonTasks.showConfirmation("You are changing your profile information!!");
                admin.getClient().sendRequest(new Request(Request.RequestType.UPDATE_INFO, admin, newData));
                Response response = admin.getClient().receiveResponse();
                if (response.getResponseType() == Response.ResponseType.SUCCESS)
                    UserData.getInstance().setUser((User) response.getData());
                else
                    CommonTasks.showError((String) response.getData());
            } catch (IOException | ClassNotFoundException e) {
                CommonTasks.showError("An unknown error acquired");
                e.printStackTrace();
            }
        } else {
            CommonTasks.showError("Current password or national ID is incorrect.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(newPhoneNumber);
    }
}
