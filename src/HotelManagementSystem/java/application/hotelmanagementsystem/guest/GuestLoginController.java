package application.hotelmanagementsystem.guest;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;

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
