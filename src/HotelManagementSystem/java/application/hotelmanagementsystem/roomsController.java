package application.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

import java.net.URL;
import java.util.ResourceBundle;

public class roomsController implements Initializable {

    @FXML
    private ChoiceBox<String> filterRoomType;

    private String[] RoomType = {"Single" , "Double" , "SUIT" , "V.I.P"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        filterRoomType.getItems().addAll(RoomType);
    }
}
