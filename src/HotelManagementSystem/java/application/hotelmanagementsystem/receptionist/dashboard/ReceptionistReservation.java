package application.hotelmanagementsystem.receptionist.dashboard;

import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistReservation implements Initializable {
    @FXML
    private TextField roomNumber;

    @FXML
    private TextField nights;

    @FXML
    private TextField nationalId;

    @FXML
    private TextField nationalId1;

    @FXML
    private TextField phoneNumber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(roomNumber);
        CommonTasks.setOnlyNumber(nights);
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(nationalId1);
        CommonTasks.setOnlyNumber(phoneNumber);
    }
}
