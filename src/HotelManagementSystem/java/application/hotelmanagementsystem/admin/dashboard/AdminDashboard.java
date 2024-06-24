package application.hotelmanagementsystem.admin.dashboard;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.admin.navbars.AdminSideNav;
import application.hotelmanagementsystem.admin.navbars.AdminTopNav;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {
    @FXML
    private BorderPane content;
    @FXML
    private BorderPane listView;
    @FXML
    private BorderPane addView;
    @FXML
    private BorderPane sideNav;
    @FXML
    private BorderPane topNav;

    @FXML
    public void loadReports() throws IOException {
        ReportView reportView = (ReportView) CommonTasks.loadPage("admin/dashboard/reports/report-view.fxml", listView);
        reportView.setDashboard(this);
    }

    @FXML
    public void loadReportSample() {
        CommonTasks.loadPage("admin/dashboard/reports/report-sample-view.fxml", addView);
    }

    @FXML
    public void loadAddReport() {
        CommonTasks.loadPage("admin/dashboard/reports/add-report-view.fxml", addView);
    }

    @FXML
    public void loadRoomView() {
        CommonTasks.loadPage("admin/dashboard/Rooms/room-view.fxml", listView);
    }

    @FXML
    public void loadStaff() {
        StaffView staffView = (StaffView) CommonTasks.loadPage("admin/dashboard/staff/staff-view.fxml", listView);
        staffView.setDashboard(this);
    }

    @FXML
    public void loadStaffSample() {
        CommonTasks.loadPage("admin/dashboard/staff/add-staff-view.fxml", addView);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AdminSideNav nav = (AdminSideNav) CommonTasks.loadPage("admin/navbars/admin-side-nav.fxml", sideNav);
        AdminTopNav navbar = (AdminTopNav) CommonTasks.loadPage("admin/navbars/admin-top-nav.fxml", topNav);
        nav.setDashboard(this);
        navbar.setDashboard(this);
    }
}
