package application.hotelmanagementsystem;

import javafx.fxml.FXML;

import java.io.IOException;

public class WelcomeController extends CloseButton {
    @FXML
    public void guestLogin() throws IOException {
        CommonTasks.pageNavigate("guest-login-view.fxml");
    }
}
