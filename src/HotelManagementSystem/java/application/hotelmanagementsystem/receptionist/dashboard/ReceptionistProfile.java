package application.hotelmanagementsystem.receptionist.dashboard;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class ReceptionistProfile extends CloseButton {
    @FXML
    private AnchorPane editInfo;

    public void edit() {
        CommonTasks.loadPage("receptionist/dashboard/profile/edit-info-view.fxml", editInfo);
    }

    public void back() {
        CommonTasks.pageNavigate("receptionist/dashboard/receptionist-dashboard.fxml");
    }
}
