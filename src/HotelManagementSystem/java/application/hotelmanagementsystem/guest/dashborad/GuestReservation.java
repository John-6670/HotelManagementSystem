package application.hotelmanagementsystem.guest.dashborad;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.receptionist.dashboard.ReceptionistReservation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestReservation implements Initializable {
    @FXML
    public ComboBox<String> roomType;

    @FXML
    private TextField numberOfNights;

    @FXML
    private TextField changedNumberOfNights;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(numberOfNights);
        CommonTasks.setOnlyNumber(changedNumberOfNights);

        roomType.getItems().add("Single");
        roomType.getItems().add("Double");
        roomType.getItems().add("VIP");
    }
}
