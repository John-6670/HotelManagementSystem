package application.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestRoomController implements Initializable {
    @FXML
    private ChoiceBox<String> service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service.getItems().add("Room Service");
        service.getItems().add("Laundry Service");
        service.getItems().add("Food Service");
    }
}
