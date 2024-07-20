package application.hotelmanagementsystem.admin.dashboard.reports;

import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import models.socket.Client;
import models.socket.Request;
import models.user.Admin;

import java.util.Map;

public class AddReport {
    @FXML
    private Label reportSubject;
    @FXML
    private TextArea reportContent;
    public void addReport() {
        Client client = Main.client;
        //try {
       //     client.sendRequest(new Request(Request.RequestType.ADD_REPORT , new Admin() , Map.of("")));
     //   }
    }
}
