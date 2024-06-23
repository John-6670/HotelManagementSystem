package application.hotelmanagementsystem.guest.navbars;

import application.hotelmanagementsystem.guest.dashborad.GuestDashboard;
import javafx.fxml.FXML;

public class GuestSideNav {
    private GuestDashboard dashboard;

    public void setDashboard(GuestDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    public void loadReservationPage() {
        dashboard.loadReservationPage();
    }

    @FXML
    public void loadRoomPage() {
        dashboard.loadRoomPage();
    }
}
