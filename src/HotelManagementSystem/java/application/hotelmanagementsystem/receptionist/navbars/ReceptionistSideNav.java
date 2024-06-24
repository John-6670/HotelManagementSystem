package application.hotelmanagementsystem.receptionist.navbars;

import application.hotelmanagementsystem.receptionist.dashboard.ReceptionistDashboard;
import javafx.fxml.FXML;

public class ReceptionistSideNav {
    private ReceptionistDashboard dashboard;

    public void setDashboard(ReceptionistDashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void loadReservation() {
        dashboard.loadReservation();
    }

    public void loadReports() {
        dashboard.loadReports();
    }

    public void loadSearchRooms() {
        dashboard.loadSearchRooms();
    }
}
