package application.hotelmanagementsystem.guest.dashborad.reservation;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.bill.Bill;
import models.room.RoomType;
import models.socket.Request;
import models.user.Guest;
import models.user.User;

import java.net.URL;
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

    // TODO: Complete this method
    public void book() {
        Guest guest = (Guest) UserData.getInstance().getUser();
        Map<String, Object> roomData = new HashMap<>();
        roomData.put("type", roomType.getSelectionModel().getSelectedItem());
//        roomData.put("")
//        guest.getClient().sendRequest(new Request(Request.RequestType.BOOK_ROOM, guest, roomData));
        CommonTasks.showConfirmation("You booked a " + roomType.getValue() + " room for " + numberOfNights.getText() + " nights for " + roomPrice.getText() + "$.");
    }

    public void changeReservation() {
        CommonTasks.showConfirmation("You are changing the reservation details to " + changedNumberOfNights.getText() + " nights.");
    }

    public void pay() {
        CommonTasks.showConfirmation("Your bill is " + totalBill.getText() + "$.\nRoom Charge: " + roomCharge.getText() + "$,  Additional Services: " + additionalServices.getText() + "$.");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
