package application.hotelmanagementsystem;

import javafx.fxml.FXML;

import java.io.IOException;

public interface LoginController {
    void goToSignUpPage() throws IOException;
    void login() throws IOException;
    void back() throws IOException;
}
