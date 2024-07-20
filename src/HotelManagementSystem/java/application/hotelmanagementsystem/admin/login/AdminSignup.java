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
import java.util.Objects;
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

    public void signup() throws IOException {
        if(CommonTasks.isAnyEmpty(fullName , userName , passWord , nationalId)){
            CommonTasks.showError("Please Fill The field correctly!");
            return;
        }
        if(!Objects.equals(passWord.getText(), repPassWord.getText())){
            CommonTasks.showError("Password and repeat Password can't be different!");
            return;
        }
        try {
            Map<String, Object> signupData = new HashMap<>();
            signupData.put("name" , fullName.getText());
            signupData.put("username" , userName.getText());
            signupData.put("password" , passWord.getText());
            signupData.put("nationalId" , nationalId.getText());
            if(!Objects.equals(phoneNumber.getText() , ""));
                signupData.put("phoneNumber" , phoneNumber.getText());
            if(!Objects.equals(email.getText(), ""))
                signupData.put("email" , email.getText());

            Client client = Main.client;
            client.sendRequest(new Request(Request.RequestType.SIGNUP , new Admin() , signupData));
            Admin admin = (Admin)client.receiveResponse().getData();
            if(admin != null){
                CommonTasks.showError("Sign up has been made!");
            }
            userName.setText("");
            fullName.setText("");
            passWord.setText("");
            repPassWord.setText("");
            phoneNumber.setText("");
            email.setText("");
            nationalId.setText("");
        } catch (IOException | ClassNotFoundException e) {
            CommonTasks.showError("Couldn't Please try later!");
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(phoneNumber);
    }
}
