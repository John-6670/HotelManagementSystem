package application.hotelmanagementsystem.admin;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;

import java.io.IOException;

public class AdminLoginController extends CloseButton {
    @FXML
    public void goToSignUpPage() throws IOException {
        CommonTasks.pageNavigate("admin/admin-signup-view.fxml");
    }

    @FXML
    public void back() throws IOException {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }
}
