package application.hotelmanagementsystem.admin.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.LoginController;

import java.io.IOException;

public class AdminLogin extends CloseButton implements LoginController {
    @Override
    public void goToSignUpPage() throws IOException {
        CommonTasks.pageNavigate("admin/login/admin-signup-view.fxml");
    }

    @Override
    public void login() throws IOException {
        CommonTasks.pageNavigate("admin/dashboard/admin-dashboard.fxml");
    }

    @Override
    public void back() throws IOException {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }
}
