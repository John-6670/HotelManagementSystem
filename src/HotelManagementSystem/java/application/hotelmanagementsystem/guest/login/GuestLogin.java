package application.hotelmanagementsystem.guest.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.LoginController;

public class GuestLogin extends CloseButton implements LoginController {
    @Override
    public void goToSignUpPage() {
        CommonTasks.pageNavigate("guest/login/guest-signUp-view.fxml");
    }

    @Override
    public void login() {
        CommonTasks.pageNavigate("guest/dashboard/guest-dashboard.fxml");
    }

    @Override
    public void back() {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }

    @Override
    public void forgetPass() {
        CommonTasks.pageNavigate("guest/login/forgot-pass-view.fxml");
    }
}
