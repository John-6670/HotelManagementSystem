package application.hotelmanagementsystem;

import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController extends CloseButton {
    @FXML
    public void guestLogin() throws IOException {
        CommonTasks.pageNavigate("guest/login/guest-login-view.fxml");
    }

    @FXML
    public void adminLogin() throws IOException {
        CommonTasks.pageNavigate("admin/login/admin-login-view.fxml");
    }

    @FXML
    public void receptionistLogin() throws IOException {
        CommonTasks.pageNavigate("receptionist/login/receptionist-login-view.fxml");
    }
}
