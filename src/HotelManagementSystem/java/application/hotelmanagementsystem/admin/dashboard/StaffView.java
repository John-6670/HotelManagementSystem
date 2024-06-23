package application.hotelmanagementsystem.admin.dashboard;

import javafx.fxml.FXML;

public class StaffView {
    private AdminDashboard dashboard;

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    public void loadStaffSample() {
        dashboard.loadStaffSample();
    }
}
