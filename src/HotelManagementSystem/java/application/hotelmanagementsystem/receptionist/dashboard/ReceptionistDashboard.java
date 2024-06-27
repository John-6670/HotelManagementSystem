package application.hotelmanagementsystem.receptionist.dashboard;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.receptionist.navbars.ReceptionistSideNav;
import application.hotelmanagementsystem.receptionist.navbars.ReceptionistTopNav;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.net.CookiePolicy;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistDashboard implements Initializable {
    @FXML
    private Pane sideNav;

    @FXML
    private Pane topNav;

    @FXML
    private BorderPane content;

    public void loadReservation() {
        CommonTasks.loadPage("receptionist/dashboard/receptionist-reservation.fxml", content);
    }

    public void loadReports() {
        CommonTasks.loadPage("receptionist/dashboard/receptionist-checkins-out-report.fxml", content);
    }

    public void loadSearchRooms() {
        CommonTasks.loadPage("admin/dashboard/Rooms/rooms-view.fxml", content);
    }

    public void showProfile() {
        CommonTasks.pageNavigate("receptionist/dashboard/profile/receptionist-profile.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReceptionistSideNav nav = (ReceptionistSideNav) CommonTasks.loadPage("receptionist/navbars/receptionist-side-nav.fxml", sideNav);
        ReceptionistTopNav navbar = (ReceptionistTopNav) CommonTasks.loadPage("receptionist/navbars/receptionist-top-nav.fxml", topNav);
        loadReservation();
        nav.setDashboard(this);
        navbar.setDashboard(this);
    }
}
