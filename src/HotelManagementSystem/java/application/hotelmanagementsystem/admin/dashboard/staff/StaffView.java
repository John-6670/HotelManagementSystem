package application.hotelmanagementsystem.admin.dashboard.staff;

import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import javafx.fxml.FXML;

public class StaffView {
    private AdminDashboard dashboard;

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    public void loadAddStaff() {
        dashboard.loadAddStaff();
    }
}
