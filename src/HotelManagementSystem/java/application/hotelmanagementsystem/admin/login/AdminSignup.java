package application.hotelmanagementsystem.admin.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminSignup extends CloseButton {
    @FXML
    public void goToLoginPage() throws IOException {
        CommonTasks.pageNavigate("admin/login/admin-login-view.fxml");
    }
}
