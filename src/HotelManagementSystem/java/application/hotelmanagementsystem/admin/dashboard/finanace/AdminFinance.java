package application.hotelmanagementsystem.admin.dashboard.finanace;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.user.Admin;
import models.user.User;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminFinance extends CloseButton implements Initializable {
    @FXML
    private Label adminSalary;
    public void back() {
        CommonTasks.pageNavigate("admin/dashboard/admin-dashboard.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Admin admin = (Admin)UserData.getInstance().getUser();
        adminSalary.setText(String.valueOf(admin.getSalary()));
    }
}
