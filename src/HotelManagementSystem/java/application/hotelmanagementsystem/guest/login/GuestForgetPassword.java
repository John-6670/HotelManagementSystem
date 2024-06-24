package application.hotelmanagementsystem.guest.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;

public class GuestForgetPassword extends CloseButton {
    public void back() {
        CommonTasks.pageNavigate("guest/login/guest-login-view.fxml");
    }
}
