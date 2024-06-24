package application.hotelmanagementsystem.guest.dashborad.room;

import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.net.CookiePolicy;
import java.net.URL;
import java.util.ResourceBundle;

public class GuestRoom implements Initializable {
    @FXML
    private ComboBox<String> service;
    @FXML
    private Text servicePrice;

    public void requestService() {
        CommonTasks.showConfirmation("You requested a " + service.getValue() + " service for " + servicePrice.getText() + "$.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        service.getItems().add("Room Service");
        service.getItems().add("Laundry Service");
        service.getItems().add("Food Service");
    }
}
