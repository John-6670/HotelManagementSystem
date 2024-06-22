package application.hotelmanagementsystem.guest.dashborad;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestRoom implements Initializable {
    @FXML
    private ComboBox<String> service;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service.getItems().add("Room Service");
        service.getItems().add("Laundry Service");
        service.getItems().add("Food Service");
    }
}
