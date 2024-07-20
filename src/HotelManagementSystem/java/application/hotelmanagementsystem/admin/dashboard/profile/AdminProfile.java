package application.hotelmanagementsystem.admin.dashboard.profile;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import models.socket.Request;
import models.socket.Response;
import models.user.Admin;
import models.user.Guest;
import models.user.User;

import java.io.IOException;
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
        Admin admin = (Admin) UserData.getInstance().getUser();
        try {
            CommonTasks.showConfirmation("You are deleting your account!!");
            admin.getClient().sendRequest(new Request(Request.RequestType.DELETE_ACCOUNT, admin, null));
            Response response = admin.getClient().receiveResponse();

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
