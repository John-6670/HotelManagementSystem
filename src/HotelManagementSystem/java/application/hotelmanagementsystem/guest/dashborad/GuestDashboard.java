package application.hotelmanagementsystem.guest.dashborad;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.guest.navbars.GuestSideNav;
import application.hotelmanagementsystem.guest.navbars.GuestTopNav;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GuestDashboard extends CloseButton implements Initializable {
    @FXML
    private BorderPane content;

    @FXML
    private BorderPane sideNav;

    @FXML
    private BorderPane topNav;

    public void loadRoomPage() {
        CommonTasks.loadPage("guest/dashboard/room.fxml", content);
    }

    public void loadReservationPage() {
        CommonTasks.loadPage("guest/dashboard/reservation.fxml", content);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GuestSideNav nav = (GuestSideNav) CommonTasks.loadPage("guest/navbars/guest-side-nav.fxml", sideNav);
        GuestTopNav navbar = (GuestTopNav) CommonTasks.loadPage("guest/navbars/guest-top-nav.fxml", topNav);
        nav.setDashboard(this);
        navbar.setDashboard(this);
    }
}
