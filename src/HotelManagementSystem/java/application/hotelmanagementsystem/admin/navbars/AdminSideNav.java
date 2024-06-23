package application.hotelmanagementsystem.admin.navbars;

import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminSideNav {
    private AdminDashboard dashboard;

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    public void loadReports() throws IOException {
        dashboard.loadReports();
    }

    @FXML
    public void loadRoomView() {
        dashboard.loadRoomView();
    }

    @FXML
    public void loadStaff() {
        dashboard.loadStaff();
    }
}
