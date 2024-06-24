package application.hotelmanagementsystem.admin.dashboard;

import application.hotelmanagementsystem.CommonTasks;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.net.URL;
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
}
