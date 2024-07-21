package application.hotelmanagementsystem.admin.dashboard.reports;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import models.report.Report;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;

import java.io.IOException;
import java.util.Map;

public class AddReport {
    @FXML
    private TextField reportSubject;
    @FXML
    private TextArea reportContent;
    public void addReport() {
        Client client = Main.client;
        try{
            client.sendRequest(new Request(Request.RequestType.ADD_REPORT , new Admin() , Map.of("Subject" , reportSubject.getText(), "Content" , reportContent.getText())));
            Report report = (Report)client.receiveResponse().getData();
            if(report != null){
                CommonTasks.showError("Report Has Been added Successfully!");
            }
            else{
                CommonTasks.showError("Couldn't Make it! try again later!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
