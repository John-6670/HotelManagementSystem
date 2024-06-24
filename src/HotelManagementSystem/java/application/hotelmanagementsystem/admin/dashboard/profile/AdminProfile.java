package application.hotelmanagementsystem.admin.dashboard.profile;

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

    public void loadFinance() {
        CommonTasks.pageNavigate("admin/dashboard/finance/finance-view.fxml");
    }

    public void quit() {
        CommonTasks.showConfirmation("Your are going to quit from the hotel!!");
    }

    public void showSecurityKey() {
        CommonTasks.loadPopUp("admin/dashboard/profile/show-admin-key.fxml");
    }
}
