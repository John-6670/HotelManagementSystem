package application.hotelmanagementsystem.guest.dashborad.profile;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.socket.Request;
import models.socket.Response;
import models.user.Guest;
import models.user.User;

import java.io.IOException;
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
        Guest guest = (Guest) UserData.getInstance().getUser();
        try {
            CommonTasks.showConfirmation("You are deleting your account!!");
            guest.getClient().sendRequest(new Request(Request.RequestType.DELETE_ACCOUNT, guest, null));
            Response response = guest.getClient().receiveResponse();

            String message;
            if (response.getResponseType() == Response.ResponseType.FAIL)
                message = (String) response.getData();
            else
                message = "This account deleted successfully";

            CommonTasks.showError(message);
        } catch (IOException | ClassNotFoundException e) {
            CommonTasks.showError("An unknown error acquired.");
            e.printStackTrace();
        }
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
