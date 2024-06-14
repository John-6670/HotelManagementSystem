package application.hotelmanagementsystem;

import javafx.fxml.FXML;

import javax.swing.*;
import java.io.IOException;

public class WelcomeController extends CloseButton {
    @FXML
    public void guestLogin() throws IOException {
        CommonTasks.pageNavigate("guest/guest-login-view.fxml");
    }

    @FXML
    public void adminLogin() throws IOException {
        CommonTasks.pageNavigate("admin/admin-login-view.fxml");
    }
}
