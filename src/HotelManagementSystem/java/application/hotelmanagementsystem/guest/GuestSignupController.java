package application.hotelmanagementsystem.guest;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;

import java.io.IOException;

public class GuestSignupController extends CloseButton {
    @FXML
    public void goToLoginPage() throws IOException {
        CommonTasks.pageNavigate("guest/guest-login-view.fxml");
    }
}
