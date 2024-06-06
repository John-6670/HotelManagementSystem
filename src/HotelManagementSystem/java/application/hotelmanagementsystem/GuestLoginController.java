package application.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;

public class GuestLoginController extends CloseButton {
    @FXML
    public void goToSignUpPage() throws IOException {
        CommonTasks.pageNavigate("guest-signUp-view.fxml");
    }

    @FXML
    public void back() throws IOException {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }
}
