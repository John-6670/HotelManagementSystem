package application.hotelmanagementsystem.receptionist.login;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;

public class ReceptionistForgetPass extends CloseButton {
    public void back() {
        CommonTasks.pageNavigate("receptionist/login/receptionist-login-view.fxml");
    }
}
