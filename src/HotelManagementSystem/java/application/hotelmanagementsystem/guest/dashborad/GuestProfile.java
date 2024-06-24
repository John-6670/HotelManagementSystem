package application.hotelmanagementsystem.guest.dashborad;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class GuestProfile extends CloseButton {
    @FXML
    private AnchorPane editInfo;

    public void back() {
        CommonTasks.pageNavigate("guest/dashboard/guest-dashboard.fxml");
    }

    public void loadEdit() {
        CommonTasks.loadPage("guest/dashboard/profile/edit-info-view.fxml", editInfo);
    }
}
