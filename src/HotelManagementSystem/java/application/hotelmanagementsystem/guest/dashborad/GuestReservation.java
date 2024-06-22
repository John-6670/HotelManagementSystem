package application.hotelmanagementsystem.guest.dashborad;

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
        numberOfNights.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                numberOfNights.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        changedNumberOfNights.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                changedNumberOfNights.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        roomType.getItems().add("Single");
        roomType.getItems().add("Double");
        roomType.getItems().add("VIP");
    }
}
