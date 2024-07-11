package application.hotelmanagementsystem.admin.dashboard.staff;

import application.hotelmanagementsystem.CommonTasks;

import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import models.socket.Client;
import models.socket.Request;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class AddStaff implements Initializable {
    @FXML
    private TextField nationalId;

    @FXML
    private TextField phoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(phoneNumber);
    }

    public void AddReceptionist(){
        Client client = Main.client;

    }
}
