package application.hotelmanagementsystem.admin.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class AdminSignup extends CloseButton implements Initializable {
    @FXML
    private TextField fullName;
    @FXML
    private TextField nationalId;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField passWord;
    @FXML
    private PasswordField repPassWord;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField email;

    @FXML
    public void goToLoginPage() {
        CommonTasks.pageNavigate("admin/login/admin-login-view.fxml");
    }

    public void signup(){
        Map<String, Object> signupData = new HashMap<>();
        signupData.put("name" , fullName.getText());
        signupData.put("username" , userName.getText());
        signupData.put("password" , passWord.getText());
        signupData.put("phoneNumber" , phoneNumber.getText());
        signupData.put("nationalId" , nationalId.getText());
        signupData.put("email" , email.getText());

        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.SIGNUP , new Admin() , signupData));

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(phoneNumber);
    }
}
