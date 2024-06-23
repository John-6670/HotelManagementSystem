package application.hotelmanagementsystem.receptionist.navbars;

import application.hotelmanagementsystem.receptionist.dashboard.ReceptionistDashboard;
import javafx.fxml.FXML;

public class ReceptionistSideNav {
    private ReceptionistDashboard dashboard;

    public void setDashboard(ReceptionistDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    public void loadReservation() {
        dashboard.loadReservation();
    }

    @FXML
    public void loadReports() {
        dashboard.loadReports();
    }
}
