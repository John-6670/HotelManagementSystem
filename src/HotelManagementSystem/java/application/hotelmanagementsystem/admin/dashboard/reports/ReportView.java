package application.hotelmanagementsystem.admin.dashboard.reports;

import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import javafx.fxml.FXML;

import java.io.IOException;

public class ReportView {
    private AdminDashboard dashboard;

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    public void loadAddReport() {
        dashboard.loadAddReport();
    }

    @FXML
    public void loadReport() throws IOException {
        dashboard.loadReportSample();
    }
}
