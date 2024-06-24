package application.hotelmanagementsystem.admin.dashboard;

import application.hotelmanagementsystem.CommonTasks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomView implements Initializable {
    @FXML
    private TextField addRoomNumber;

    @FXML
    private TextField updateRoomNumber;

    @FXML
    private TextField deleteRoomNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(addRoomNumber);
        CommonTasks.setOnlyNumber(updateRoomNumber);
        CommonTasks.setOnlyNumber(deleteRoomNumber);
    }
}