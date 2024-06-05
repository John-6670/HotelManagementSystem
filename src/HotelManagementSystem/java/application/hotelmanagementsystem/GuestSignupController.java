package application.hotelmanagementsystem;

import javafx.fxml.FXML;

import java.io.IOException;

public class GuestSignupController extends CloseButton {
    @FXML
    public void back() throws IOException {
        CommonTasks.pageNavigate("guest-login-view.fxml", Main.stage, this.getClass(), "Guest Login");
    }
}