package application.hotelmanagementsystem.admin.dashboard.room;

import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomSearch implements Initializable {
    @FXML
    private TextField from;

    @FXML
    private TextField to;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(from);
        CommonTasks.setOnlyNumber(to);
    }
}
