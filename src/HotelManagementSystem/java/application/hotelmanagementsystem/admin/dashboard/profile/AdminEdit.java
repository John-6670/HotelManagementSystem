package application.hotelmanagementsystem.admin.dashboard.profile;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;
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
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.CHECK_If_PASS_IS_VALID , new Admin() , Map.of("password" , oldPassword.getText() , "nationalId" , nationalId.getText())));
            Admin admin = (Admin)client.receiveResponse().getData();
            if(admin != null){
                try{
                    Map<String,Object> data = new HashMap<>();
                    if(newEmail.getText() != null)
                        data.put("email" , newEmail.getText());
                    if(newPhoneNumber.getText() != null)
                        data.put("phoneNumber" , newPhoneNumber.getText());
                    if(Objects.equals(newPassword.getText(), newRepPassword.getText()) && newPassword.getText() != null)
                        data.put("password" , newPassword.getText());
                    else if(Objects.equals(newPassword.getText(), newRepPassword.getText())){
                        CommonTasks.showError("Password and Repeat doesn't match!");
                        return;
                    }

                    client.sendRequest(new Request(Request.RequestType.UPDATE_INFO, new Admin(), data));
                    User user = (User)client.receiveResponse().getData();
                    if(user != null)
                        CommonTasks.showError("Information has been updated successfully!");
                    else
                        CommonTasks.showError("Couldn't update your Information!");
                    nationalId.setText("");
                    oldPassword.setText("");
                    newPassword.setText("");
                    newRepPassword.setText("");
                    newEmail.setText("");
                    newPhoneNumber.setText("");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            else{
                CommonTasks.showError("Password or NationalId is Wrong!");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(newPhoneNumber);
    }
}
