package application.hotelmanagementsystem.receptionist.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.LoginController;

import java.io.IOException;

public class ReceptionistLogin extends CloseButton implements LoginController {
    @Override
    public void goToSignUpPage() throws IOException {

    }

    @Override
    public void login() throws IOException {
        CommonTasks.pageNavigate("receptionist/dashboard/receptionist-dashboard.fxml");
    }

    @Override
    public void back() throws IOException {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }
}
