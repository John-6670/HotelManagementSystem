package application.hotelmanagementsystem.guest.dashborad.room;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;
import models.bill.Bill;
import models.room.Room;
import models.socket.Request;
import models.socket.Response;
import models.user.Guest;
import models.service.Services;

import java.io.IOException;
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
    private Guest guest;

    public void requestService() {
        if (guest.getBill() == null) {
            CommonTasks.showError("You don't have room!!");
        } else {
            try {
                Services service = Services.valueOf(services.getSelectionModel().getSelectedItem().toUpperCase().replace(' ', '_'));
                guest.getClient().sendRequest(new Request(Request.RequestType.REQUEST_SERVICE, guest, service));
                CommonTasks.showConfirmation("You requested a " + services.getValue() + " service for " + servicePrice.getText() + "$.");
                Response response = guest.getClient().receiveResponse();
                if (response.getResponseType() == Response.ResponseType.SUCCESS)
                    showNewBill(service);
                else
                    CommonTasks.showError((String) response.getData());
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                CommonTasks.showError("An unknown error acquired!");
            }
        }
    }

    private void showNewBill(Services service) {
        String additionalPrice = additionalServices.getText();
        String totalPrice = totalBill.getText();
        additionalServices.setText(CommonTasks.intOrDouble(Double.valueOf(additionalPrice) + service.getPrice()));
        totalBill.setText(CommonTasks.intOrDouble(Double.valueOf(totalPrice) + service.getPrice()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guest = (Guest) UserData.getInstance().getUser();
        for (Services service : Services.values()) {
            services.getItems().add(service.toString());
        }

        services.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                Services selectedService = Services.valueOf(newValue.replace(' ', '_').toUpperCase());
                servicePrice.setText(CommonTasks.intOrDouble(selectedService.getPrice()));
            }
        });

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
