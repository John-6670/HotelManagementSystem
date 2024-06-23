package application.hotelmanagementsystem.receptionist.dashboard;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.receptionist.navbars.ReceptionistSideNav;
import application.hotelmanagementsystem.receptionist.navbars.ReceptionistTopNav;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistDashboard implements Initializable {
    @FXML
    private BorderPane sideNav;

    @FXML
    private BorderPane topNav;

    @FXML
    private BorderPane content;

    @FXML
    public void loadReservation() {
        CommonTasks.loadPage("receptionist/dashboard/receptionist-reservation.fxml", content);
    }

    @FXML
    public void loadReports() {
        CommonTasks.loadPage("receptionist/dashboard/receptionist-checkins-out-report.fxml", content);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReceptionistSideNav nav = (ReceptionistSideNav) CommonTasks.loadPage("receptionist/navbars/receptionist-side-nav.fxml", sideNav);
        ReceptionistTopNav navbar = (ReceptionistTopNav) CommonTasks.loadPage("receptionist/navbars/receptionist-top-nav.fxml", topNav);
        nav.setDashboard(this);
        navbar.setDashboard(this);
    }
}
