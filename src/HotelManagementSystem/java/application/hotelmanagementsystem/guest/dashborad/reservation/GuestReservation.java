package application.hotelmanagementsystem.guest.dashborad.reservation;

import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
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

    public void book() {
        CommonTasks.showConfirmation("You booked a " + roomType.getValue() + " room for " + numberOfNights.getText() + " nights for " + roomPrice.getText() + "$.");
    }

    public void changeReservation() {
        CommonTasks.showConfirmation("You are changing the reservation details to " + changedNumberOfNights.getText() + " nights.");
    }

    public void pay() {
        CommonTasks.showConfirmation("Your bill is " + totalBill.getText() + "$.\nRoom Charge: " + roomCharge.getText() + "$,  Additional Services: " + additionalServices.getText() + "$.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(numberOfNights);
        CommonTasks.setOnlyNumber(changedNumberOfNights);

        roomType.getItems().add("Single");
        roomType.getItems().add("Double");
        roomType.getItems().add("VIP");
    }
}
