package application.hotelmanagementsystem.admin.dashboard.room;

import application.hotelmanagementsystem.CommonTasks;

import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import com.j256.ormlite.stmt.query.In;
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
import java.util.Objects;
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
    @FXML
    private Label roomSitWhenCantDelete;
    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void loadRooms() {
        dashboard.loadSearchRooms();
    }

    public void addRoom() throws SQLException {
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
        Client client = Main.client;
        try{
            client.sendRequest(new Request(Request.RequestType.SEARCH_IF_ROOM_EXISTS, new Admin(), Map.of("room_number",Integer.parseInt(updateRoomNumber.getText()))));
            Room room = (Room) client.receiveResponse().getData();
            if(room != null){
                currentStatus.setText(getStatus(room.getStatus()));
                newStatus.getItems().add("Available");
                newStatus.getItems().add("Booked");
                newStatus.getItems().add("Full");
                newStatus.getItems().add("Under Maintenance");
                newStatus.getItems().add("Out of Order");
                newStatus.getItems().remove(room.getStatus(room.getStatus()));
            }
        }catch (IOException e){
            CommonTasks.showError("Couldn't find Room");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public void updateRoom() {
        Client client = Main.client;
        try{
            client.sendRequest(new Request(Request.RequestType.UPDATE_ROOM ,new Admin(), Map.of( "room_number" , Integer.parseInt(updateRoomNumber.getText()) ,"status" , getStatus((String) newStatus.getSelectionModel().getSelectedItem()))));
            Room room = (Room) client.receiveResponse().getData();
            if(room != null){
                CommonTasks.showError("Room is Updated Now!");
            }else{
                CommonTasks.showError("Couldn't make it! Try again later!");
            }
            updateRoomNumber.setText("");
            newStatus.getSelectionModel().select(-1);
            newStatus.getItems().clear();
        }catch (IOException e){
            CommonTasks.showError("Couldn't make it! Try again later!");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public void deleteRoom() {
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.SEARCH_IF_ROOM_EXISTS , new Admin() , Map.of("room_number" , Integer.parseInt(deleteRoomNumber.getText()))));
            Room room = (Room) client.receiveResponse().getData();
            if(room.getStatus().equals(Status.BOOKED) || room.getStatus().equals(Status.FULLED)){
                roomSitWhenCantDelete.setText(getStatus(room.getStatus()));
                deleteRoomNumber.setText("");
            }
            else
                removeRoom();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void removeRoom(){
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.REMOVE_ROOM , new Admin() , Map.of("room_number" , Integer.parseInt(deleteRoomNumber.getText()))));
            Room room = (Room) client.receiveResponse().getData();
            if(room != null)
                CommonTasks.showError("Room has been deleted");
            else
                CommonTasks.showError("Couldn't make it! Try again later!");
            deleteRoomNumber.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
