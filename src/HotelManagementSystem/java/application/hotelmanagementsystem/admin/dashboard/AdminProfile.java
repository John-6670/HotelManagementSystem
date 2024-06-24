package application.hotelmanagementsystem.admin.dashboard;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class AdminProfile extends CloseButton {
    @FXML
    private AnchorPane editInfo;

    public void back() {
        CommonTasks.pageNavigate("admin/dashboard/admin-dashboard.fxml");
    }

    public void loadEdit() {
        CommonTasks.loadPage("admin/dashboard/profile/edit-info-view.fxml", editInfo);
    }
}
