package application.hotelmanagementsystem.admin.dashboard.room;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.room.Room;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomSearch extends Room implements Initializable {
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private ComboBox<String> roomType;
    @FXML
    private ComboBox<String> roomStatus;
    @FXML
    private ListView roomsList;
    public void loadRooms() throws IOException {
        roomsList.getItems().clear();
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.GET_ALL_ROOMS , new Admin() , Map.of("type" , getType(roomType.getSelectionModel().getSelectedItem()) , "status" , getStatus((String)roomStatus.getSelectionModel().getSelectedItem()) , "min" , Integer.parseInt(from.getText()) , "max" , Integer.parseInt(to.getText()))));
            List<Room> rooms = (List<Room>) client.receiveResponse().getData();
            for(Room room : rooms){
                roomsList.getItems().add(room.getRoomNumber() + " - " + roomType.getSelectionModel().getSelectedItem() + " - " + roomStatus.getSelectionModel().getSelectedItem() + " - " + room.getPrice());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> optionsForType = FXCollections.observableArrayList(
                "Single", "Double", "Triple" , "Quadruple" , "Suit" , "V.I.P"
        );
        ObservableList<String> optionsForStatus = FXCollections.observableArrayList(
                "Available" , "Booked" , "Full" , "Out of Order" , "Under Maintenance"
        );
        roomType.setItems(optionsForType);
        roomStatus.setItems(optionsForStatus);
        CommonTasks.setOnlyNumber(from);
        CommonTasks.setOnlyNumber(to);

        roomType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    loadRooms();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        roomStatus.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                try {
                    loadRooms();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        from.textProperty().addListener((observableValue, s, t1) -> {
            try {
                loadRooms();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        to.textProperty().addListener((observableValue, s, t1) -> {
            try {
                loadRooms();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
