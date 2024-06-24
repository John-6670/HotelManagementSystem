package application.hotelmanagementsystem.guest.dashborad.profile;

import application.hotelmanagementsystem.CommonTasks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestEdit implements Initializable {
    @FXML
    private TextField nationalId;
    @FXML
    private TextField phoneNumber;

    public void edit() {
        CommonTasks.showConfirmation("You are changing your profile information!!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(phoneNumber);
    }
}
