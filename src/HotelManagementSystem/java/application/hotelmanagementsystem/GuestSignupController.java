package application.hotelmanagementsystem;

import javafx.fxml.FXML;

import java.io.IOException;

public class GuestSignupController extends CloseButton {
    @FXML
    public void goToLoginPage() throws IOException {
        CommonTasks.pageNavigate("guest-login-view.fxml");
    }
}
