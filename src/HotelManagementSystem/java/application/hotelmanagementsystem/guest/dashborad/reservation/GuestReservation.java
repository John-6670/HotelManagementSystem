package application.hotelmanagementsystem.guest.dashborad.reservation;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.bill.Bill;
import models.room.RoomType;
import models.socket.Request;
import models.socket.Response;
import models.user.Guest;
import models.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GuestReservation implements Initializable {
    @FXML
    public ComboBox<String> roomType;
    @FXML
    private TextField numberOfNights;
    @FXML
    private TextField changedNumberOfNights;
    @FXML
    private Text roomPrice;
    @FXML
    private Text totalBill;
    @FXML
    private Text additionalServices;
    @FXML
    private Text roomCharge;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker changedDate;
    private Guest guest;

    public void book() {
        Map<String, Object> roomData = new HashMap<>();
        roomData.put("type", roomType.getSelectionModel().getSelectedItem());
        roomData.put("startDate", startDate.getValue());
        roomData.put("nights", numberOfNights.getText());
        try {
            guest.makeReservation(roomData);
        } catch (IOException e) {
            CommonTasks.showError("An unknown error acquired!");
            e.printStackTrace();
        }
        CommonTasks.showConfirmation("You booked a " + roomType.getValue() + " room for " + numberOfNights.getText() + " nights for " + roomPrice.getText() + "$.");
    }

    public void changeReservation() {
        CommonTasks.showConfirmation("You are changing the reservation details to " + changedNumberOfNights.getText() + " nights.");
    }

    public void pay() {
        try {
            guest.getClient().sendRequest(new Request(Request.RequestType.PAY_BILL, guest, null));
            CommonTasks.showConfirmation("Your bill is " + totalBill.getText() + "$.\nRoom Charge: " + roomCharge.getText() + "$,  Additional Services: " + additionalServices.getText() + "$.");
            Response response = guest.getClient().receiveResponse();
            if (response.getResponseType() == Response.ResponseType.SUCCESS) {
                updateBill();
            } else {
                CommonTasks.showError((String) response.getData());
            }
        } catch (IOException | ClassNotFoundException e) {
            CommonTasks.showError("An unknown error acquired!!");
        }
    }

    private void updateRoomPrice() {
        if (!numberOfNights.getText().isEmpty() && roomType.getSelectionModel().getSelectedItem() != null) {
            RoomType type = RoomType.valueOf(roomType.getSelectionModel().getSelectedItem().toUpperCase());
            double price = type.getPrice() * Integer.parseInt(numberOfNights.getText());
            roomPrice.setText(CommonTasks.intOrDouble(price));
        } else {
            roomPrice.setText("-");
        }
    }

    private void updateBill() {
        Bill bill = guest.getBill();
        additionalServices.setText(CommonTasks.intOrDouble(bill.getAdditionalServices()));
        roomPrice.setText(CommonTasks.intOrDouble(bill.getRoomCharge()));
        totalBill.setText(CommonTasks.intOrDouble(bill.calculateBill()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guest = (Guest) UserData.getInstance().getUser();
        CommonTasks.setOnlyNumber(numberOfNights);
        CommonTasks.setOnlyNumber(changedNumberOfNights);

        for (RoomType type : RoomType.values()) {
            roomType.getItems().add(type.toString());
        }

        roomType.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateRoomPrice()
        );
        numberOfNights.textProperty().addListener(
                (observable, oldValue, newValue) -> updateRoomPrice()
        );

        Bill bill = ((Guest) UserData.getInstance().getUser()).getBill();
        if (bill != null) {
            additionalServices.setText(CommonTasks.intOrDouble(bill.getAdditionalServices()));
            roomCharge.setText(CommonTasks.intOrDouble(bill.getRoomCharge()));
            totalBill.setText(CommonTasks.intOrDouble(bill.calculateBill()));
        }
    }
}
