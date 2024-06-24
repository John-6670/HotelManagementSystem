package application.hotelmanagementsystem.guest.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.LoginController;
import javafx.fxml.FXML;

import java.io.IOException;

public class GuestLogin extends CloseButton implements LoginController {
    @FXML
    public void goToSignUpPage() {
        CommonTasks.pageNavigate("guest/login/guest-signUp-view.fxml");
    }

    @FXML
    public void login() {
        CommonTasks.pageNavigate("guest/dashboard/guest-dashboard.fxml");
    }

    @Override
    public void back() {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }
}
