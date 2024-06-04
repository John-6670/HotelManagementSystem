package application.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GuestLoginController extends CloseButton{
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
