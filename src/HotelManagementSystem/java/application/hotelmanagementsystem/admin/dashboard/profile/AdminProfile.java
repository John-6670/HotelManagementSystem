package application.hotelmanagementsystem.admin.dashboard.profile;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.user.User;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminProfile extends CloseButton implements Initializable {
    @FXML
    private AnchorPane editInfo;
    @FXML
    private Label showUsername;
    @FXML
    private Label showFullName;
    @FXML
    private Label showEmail;
    @FXML
    private Label showPhoneNumber;


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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = UserData.getInstance().getUser();
        showUsername.setText(user.getUsername());
        showFullName.setText(user.getName());

        if (user.getEmail() != null) {
            showEmail.setText(user.getEmail());
        }

        if (user.getPhoneNumber() != null) {
            showPhoneNumber.setText(user.getPhoneNumber());
        }
    }
}
