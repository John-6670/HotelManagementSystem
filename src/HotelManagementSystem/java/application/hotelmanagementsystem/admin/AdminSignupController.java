package application.hotelmanagementsystem.admin;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminSignupController extends CloseButton {
    @FXML
    public void goToLoginPage() throws IOException {
        CommonTasks.pageNavigate("admin/admin-login-view.fxml");
    }
}
