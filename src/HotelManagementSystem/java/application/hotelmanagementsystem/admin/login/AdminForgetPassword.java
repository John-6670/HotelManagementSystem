package application.hotelmanagementsystem.admin.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;

public class AdminForgetPassword extends CloseButton {
    public void back() {
        CommonTasks.pageNavigate("admin/login/admin-login-view.fxml");
    }
}
