package application.hotelmanagementsystem.guest.dashborad.profile;

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

public class GuestProfile extends CloseButton implements Initializable {
    @FXML
    private Label username;
    @FXML
    private Label name;
    @FXML
    private Label email;
    @FXML
    private Label phoneNumber;
    @FXML
    private AnchorPane editInfo;

    public void deleteAccount() {
        CommonTasks.showConfirmation("You are deleting your account!!");
    }

    public void back() {
        CommonTasks.pageNavigate("guest/dashboard/guest-dashboard.fxml");
    }

    public void loadEdit() {
        CommonTasks.loadPage("guest/dashboard/profile/edit-info-view.fxml", editInfo);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = UserData.getInstance().getUser();
        username.setText(user.getUsername());
        name.setText(user.getName());

        if (user.getEmail() != null) {
            email.setText(user.getEmail());
        }

        if (user.getPhoneNumber() != null) {
            phoneNumber.setText(user.getPhoneNumber());
        }
    }
}
