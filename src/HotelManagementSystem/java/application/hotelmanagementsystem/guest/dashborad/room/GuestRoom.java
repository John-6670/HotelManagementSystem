package application.hotelmanagementsystem.guest.dashborad.room;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import models.bill.Bill;
import models.room.Room;
import models.user.Guest;
import models.service.Services;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestRoom implements Initializable {
    @FXML
    private Text roomNumber;
    @FXML
    private Text roomType;
    @FXML
    private Text price;
    @FXML
    private Text capacity;
    @FXML
    private ComboBox<String> services;
    @FXML
    private Text servicePrice;
    @FXML
    private Text roomCharge;
    @FXML
    private Text additionalServices;
    @FXML
    private Text totalBill;

    public void requestService() {
        CommonTasks.showConfirmation("You requested a " + services.getValue() + " service for " + servicePrice.getText() + "$.");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Services service : Services.values()) {
            services.getItems().add(service.toString());
        }

        services.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                Services selectedService = Services.valueOf(newValue.replace(' ', '_').toUpperCase());
                servicePrice.setText(CommonTasks.intOrDouble(selectedService.getPrice()));
            }
        });

        Guest guest = (Guest) UserData.getInstance().getUser();
        Room room = guest.getRoom();
        if (room != null) {
            roomNumber.setText(String.valueOf(room.getRoomNumber()));
            roomType.setText(room.getType().name().charAt(0) + room.getType().name().substring(1).toLowerCase());
            price.setText(CommonTasks.intOrDouble(room.getPrice()));
            capacity.setText(String.valueOf(room.getCapacity()));
        }

        Bill bill = guest.getBill();
        if (bill != null) {
            roomCharge.setText(CommonTasks.intOrDouble(bill.getRoomCharge()));
            additionalServices.setText(CommonTasks.intOrDouble(bill.getAdditionalServices()));
            totalBill.setText(CommonTasks.intOrDouble(bill.calculateBill()));
        }
    }
}
