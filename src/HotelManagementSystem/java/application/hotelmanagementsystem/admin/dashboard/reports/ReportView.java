package application.hotelmanagementsystem.admin.dashboard.reports;

import application.hotelmanagementsystem.Main;
import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import models.report.Report;
import models.room.Room;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ReportView implements Initializable{
    private AdminDashboard dashboard;

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @FXML
    public void loadAddReport() {
        dashboard.loadAddReport();
    }
    @FXML
    private ListView reportList;

    @FXML
    public void loadReport() throws IOException {
        dashboard.loadReportSample();
    }

    public void loadAllReports() throws IOException {
        reportList.getItems().clear();
        Client client = Main.client;
        try {
            client.sendRequest(new Request(Request.RequestType.GET_ALL_REPORTS , new Admin() , null));
            List<Report> reports = (List<Report>)client.receiveResponse().getData();
            for(Report report : reports) {
                reportList.getItems().add(report.getSubject() + " - " + report.getContent());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
