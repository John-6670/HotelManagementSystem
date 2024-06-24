package application.hotelmanagementsystem.admin.dashboard.finanace;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;

public class AdminFinance extends CloseButton {
    public void back() {
        CommonTasks.pageNavigate("admin/dashboard/admin-dashboard.fxml");
    }
}
