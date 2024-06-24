package application.hotelmanagementsystem.admin.dashboard;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.admin.navbars.AdminSideNav;
import application.hotelmanagementsystem.admin.navbars.AdminTopNav;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {
    @FXML
    private BorderPane listView;
    @FXML
    private BorderPane addView;
    @FXML
    private Pane sideNav;
    @FXML
    private Pane topNav;

    public void loadReports() throws IOException {
        listView.setPrefWidth(150.0);
        ReportView reportView = (ReportView) CommonTasks.loadPage("admin/dashboard/reports/report-view.fxml", listView);
        reportView.setDashboard(this);
    }

    public void loadReportSample() {
        CommonTasks.loadPage("admin/dashboard/reports/report-sample-view.fxml", addView);
    }

    public void loadAddReport() {
        CommonTasks.loadPage("admin/dashboard/reports/add-report-view.fxml", addView);
    }

    public void loadRoomView() {
        listView.setPrefWidth(850.0);
        RoomView roomView = (RoomView) CommonTasks.loadPage("admin/dashboard/Rooms/room-view.fxml", listView);
        roomView.setDashboard(this);
    }

    public void loadStaff() {
        listView.setPrefWidth(150.0);
        StaffView staffView = (StaffView) CommonTasks.loadPage("admin/dashboard/staff/staff-view.fxml", listView);
        staffView.setDashboard(this);
    }

    public void loadAddStaff() {
        CommonTasks.loadPage("admin/dashboard/staff/add-staff-view.fxml", addView);
    }

    public void loadSearchRooms() {
        CommonTasks.loadPage("admin/dashboard/Rooms/rooms-view.fxml", listView);
    }

    public void showProfile() {
        CommonTasks.pageNavigate("admin/dashboard/profile/profile-view.fxml");
    }

    public void showFinance() {
        CommonTasks.pageNavigate("admin/dashboard/finance/finance-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AdminSideNav nav = (AdminSideNav) CommonTasks.loadPage("admin/navbars/admin-side-nav.fxml", sideNav);
        AdminTopNav navbar = (AdminTopNav) CommonTasks.loadPage("admin/navbars/admin-top-nav.fxml", topNav);
        nav.setDashboard(this);
        navbar.setDashboard(this);
    }
}
