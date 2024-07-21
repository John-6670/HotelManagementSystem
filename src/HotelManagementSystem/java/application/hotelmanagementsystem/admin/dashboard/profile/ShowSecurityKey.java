package application.hotelmanagementsystem.admin.dashboard.profile;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class ShowSecurityKey extends CloseButton {
    @FXML
    private TextField nationalId;
    @FXML
    private TextField password;
    @FXML
    private TextField adminKey;

    public void closeApp(){
        closeWindow();
    }
    public void Submit(){
        if(!Objects.equals(nationalId.getText(), "") && !Objects.equals(password.getText(), "")){
            Client client = Main.client;
            try {
                client.sendRequest(new Request(Request.RequestType.GET_ADMIN_KEY , new Admin() , Map.of("password" , password.getText() , "nationalId" , nationalId.getText())));
                Admin admin = (Admin) client.receiveResponse().getData();
                if(admin != null){
                    adminKey.setText(CommonTasks.addSpaces(admin.getSecurityKey()));
                }else{
                    CommonTasks.showError("Password or NationalId doesn't match!");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
