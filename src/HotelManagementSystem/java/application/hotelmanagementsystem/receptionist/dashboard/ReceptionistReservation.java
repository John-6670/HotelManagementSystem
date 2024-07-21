package application.hotelmanagementsystem.receptionist.dashboard;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.UserData;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.checkInsOuts.CheckIn;
import models.checkInsOuts.CheckOut;
import models.reservation.Reservation;
import models.room.Room;
import models.socket.Client;
import models.socket.Request;
import models.user.Guest;
import models.user.Receptionist;
import models.user.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReceptionistReservation implements Initializable {
    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField name;

    @FXML
    private TextField nights;

    @FXML
    private TextField nationalId;

    @FXML
    private TextField nationalId1;

    @FXML
    private TextField phoneNumber1;

    @FXML
    private Text roomCharge ;

    @FXML
    private Text additionalService ;

    @FXML
    private Text total ;

    @FXML
    private Text roomNumber ;

    @FXML
    private Text roomType ;

    @FXML
    private Text checkInDate ;

    @FXML
    private Text checkOutDate ;
    @FXML
    private DatePicker datePicker ;
    @FXML
    private DatePicker datePicker1 ;
    @FXML
    private DatePicker datePicker2 ;
    @FXML
    private ListView<String> listView;
    @FXML
    private ListView<String> listView1;






    public void checkIn (){
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.CHECK_IN , new Receptionist() , Map.of("name", name.getText() ,"password", nationalId.getText())));
            Guest guest = (Guest) client.receiveResponse().getData();
            if (guest != null) {
                roomNumber.setText(CommonTasks.intOrDouble(guest.getRoom().getRoomNumber()));
                roomType.setText(guest.getRoom().getType().toString());
                checkInDate.setText(guest.getReservation().getStartDate().toString());
                checkOutDate.setText(guest.getReservation().getEndDate().toString());


            }else {
                CommonTasks.showError("Name  or NationalId is incorrect");
            }

        }
        catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void checkOut (){
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.CHECK_OUT , new Receptionist() , Map.of("phoneNumber", phoneNumber.getText() )));
            Guest guest = (Guest) client.receiveResponse().getData();
            if (guest != null) {
                roomCharge.setText(CommonTasks.intOrDouble(guest.getBill().getRoomCharge()));
                additionalService.setText(CommonTasks.intOrDouble(guest.getBill().getAdditionalServices()));
                total.setText(CommonTasks.intOrDouble(guest.getBill().calculateBill()));



            }else {
                CommonTasks.showError("PhoneNumber is incorrect");
            }

        }
        catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }



    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nights);
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(nationalId1);
        CommonTasks.setOnlyNumber(phoneNumber1);
    }

    public void checkInReport (){

        listView1.getItems().clear();
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.CHECK_IN_CHECK_OUT_REPORT , new Receptionist() , Map.of("startDate", datePicker1.getValue() ,"endDate", datePicker2.getValue()) ));
            List<CheckIn> checkIns = (List<CheckIn>) client.receiveResponse().getData();
            for (CheckIn checkIn : checkIns){
                listView1.getItems().add(CommonTasks.intOrDouble(checkIn.getRoom().getRoomNumber() ) + "-" + checkIn.getRoom().getType() + "-" + checkIn.getGuest().getUsername() + "-" + checkIn.getGuest().getEmail());
            }

        }
        catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    public void checkOutReport (){

        listView1.getItems().clear();
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.CHECK_IN_CHECK_OUT_REPORT , new Receptionist() , Map.of("startDate", datePicker1.getValue() ,"endDate", datePicker2.getValue()) ));
            List<CheckOut> checkOuts = (List<CheckOut>) client.receiveResponse().getData();
            for (CheckOut checkOut : checkOuts){
                listView1.getItems().add(CommonTasks.intOrDouble(checkOut.getRoom().getRoomNumber() ) + "-" + checkOut.getRoom().getType() + "-" + checkOut.getGuest().getUsername() + "-" + checkOut.getGuest().getEmail());
            }

        }
        catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }



    public void billReport (){

        listView.getItems().clear();
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.BILL_REPORT , new Receptionist() , Map.of("date", datePicker.getValue())));
            List<Reservation> reservations = (List<Reservation>) client.receiveResponse().getData();
            for (Reservation reservation : reservations){
                listView.getItems().add(CommonTasks.intOrDouble(reservation.getGuest().getBill().calculateBill()));
            }

        }
        catch (IOException e) {

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }


}
