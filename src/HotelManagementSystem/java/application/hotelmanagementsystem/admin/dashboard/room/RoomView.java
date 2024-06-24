package application.hotelmanagementsystem.admin.dashboard.room;

import application.hotelmanagementsystem.CommonTasks;

import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomView implements Initializable {
    private AdminDashboard dashboard;
    @FXML
    private TextField addRoomNumber;
    @FXML
    private TextField updateRoomNumber;
    @FXML
    private TextField deleteRoomNumber;
    @FXML
    private ListView<String> roomTypeList;
    @FXML
    private Label currentStatus;
    @FXML
    private ListView<String> newStatus;

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void loadRooms() {
        dashboard.loadSearchRooms();
    }

    public void addRoom() {
        CommonTasks.showConfirmation("Room with room number " + addRoomNumber.getText() + " and type " + roomTypeList.getSelectionModel().getSelectedItem() + ".");
    }

    public void updateRoom() {
        CommonTasks.showConfirmation("Room " + updateRoomNumber.getText() + "is going to change from " + currentStatus.getText() + " to " + newStatus.getSelectionModel().getSelectedItem() + ".");
    }

    public void deleteRoom() {
        CommonTasks.showConfirmation("You are deleting room " + deleteRoomNumber.getText() + ".");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(addRoomNumber);
        CommonTasks.setOnlyNumber(updateRoomNumber);
        CommonTasks.setOnlyNumber(deleteRoomNumber);

        roomTypeList.getItems().add("Single");
        roomTypeList.getItems().add("Double");
    }
}
