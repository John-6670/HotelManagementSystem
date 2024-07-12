package application.hotelmanagementsystem.admin.dashboard.room;

import application.hotelmanagementsystem.CommonTasks;

import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.room.Room;
import models.room.RoomType;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;
import models.user.Guest;
import models.user.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;

public class RoomView extends Room implements Initializable {
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
    private ListView newStatus;

    private ObservableList<String> Status = FXCollections.observableArrayList("Available" , "Booked" , "Fulled" , "Out of Order" , "Under Maintenance");

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void loadRooms() {
        dashboard.loadSearchRooms();
    }

    public void addRoom() throws SQLException {
        CommonTasks.showConfirmation("Room with room number " + addRoomNumber.getText() + " and type " + roomTypeList.getSelectionModel().getSelectedItem() + ".");
        Client client = Main.client;
        try{
            client.sendRequest(new Request(Request.RequestType.ADD_ROOM , new Admin() , Map.of("room_number" , Integer.parseInt(addRoomNumber.getText()) , "type" , getType(roomTypeList.getSelectionModel().getSelectedItem()))));
            Room room = (Room) client.receiveResponse().getData();
            if(room != null){
                CommonTasks.showError("Room has been Created!");
                addRoomNumber.setText("");
                roomTypeList.getSelectionModel().select(-1);
            }
            else{
                CommonTasks.showError("Room has not been Created!");
            }
        }catch (IOException e){
            CommonTasks.showError("Couldn't make it! Try again later!");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void searchRoom(){
        CommonTasks.showConfirmation("Room " + updateRoomNumber.getText() + "is going to change from " + currentStatus.getText() + " to " + newStatus.getSelectionModel().getSelectedItem() + ".");
        Client client = Main.client;
        try{
            client.sendRequest(new Request(Request.RequestType.UPDATE_ROOM, new Admin(), Map.of("room number", addRoomNumber.getText())));
            Room room = (Room) client.receiveResponse().getData();
            if(room != null){
                currentStatus.setText(getStatus(room.getStatus()));
            }
        }catch (IOException e){
            CommonTasks.showError("Couldn't find Room");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void updateRoom() {

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
        roomTypeList.getItems().add("Triple");
        roomTypeList.getItems().add("Quadruple");
        roomTypeList.getItems().add("Suit");
        roomTypeList.getItems().add("V.I.P");
    }
}
