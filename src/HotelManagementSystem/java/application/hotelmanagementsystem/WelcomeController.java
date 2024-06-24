package application.hotelmanagementsystem;

import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController extends CloseButton {
    public void guestLogin() {
        CommonTasks.pageNavigate("guest/login/guest-login-view.fxml");
    }

    public void adminLogin() {
        CommonTasks.pageNavigate("admin/login/admin-login-view.fxml");
    }

    public void receptionistLogin() {
        CommonTasks.pageNavigate("receptionist/login/receptionist-login-view.fxml");
    }
}
