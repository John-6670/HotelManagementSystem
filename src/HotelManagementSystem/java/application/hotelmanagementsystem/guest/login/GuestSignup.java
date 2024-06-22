package application.hotelmanagementsystem.guest.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;

import java.io.IOException;

public class GuestSignup extends CloseButton {
    @FXML
    public void goToLoginPage() throws IOException {
        CommonTasks.pageNavigate("guest/login/guest-login-view.fxml");
    }
}
