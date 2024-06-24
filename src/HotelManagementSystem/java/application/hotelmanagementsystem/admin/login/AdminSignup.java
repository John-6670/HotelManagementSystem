package application.hotelmanagementsystem.admin.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSignup extends CloseButton implements Initializable {
    @FXML
    private TextField nationalId;

    @FXML
    private TextField phoneNumber;

    @FXML
    public void goToLoginPage() throws IOException {
        CommonTasks.pageNavigate("admin/login/admin-login-view.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommonTasks.setOnlyNumber(nationalId);
        CommonTasks.setOnlyNumber(phoneNumber);
    }
}
