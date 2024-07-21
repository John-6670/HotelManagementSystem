package application.hotelmanagementsystem.receptionist.dashboard;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import application.hotelmanagementsystem.UserData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.user.Receptionist;
import models.user.User;

import java.net.URL;
import java.util.ResourceBundle;

public class ReceptionistProfile extends CloseButton {
    @FXML
    private AnchorPane editInfo;
    @FXML
    private Label userName;
    @FXML
    private Label name;

    @FXML
    private Text email;
    @FXML
    private Text phoneNumber;
    @FXML
    private Text monthSalary;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = UserData.getInstance().getUser();
        userName.setText(user.getUsername());
        name.setText(user.getName());

        if (user.getEmail() != null) {
            email.setText(user.getEmail());
        }

        if (user.getPhoneNumber() != null) {
            phoneNumber.setText(user.getPhoneNumber());
        }
        if (user instanceof Receptionist) {
            Receptionist receptionist = (Receptionist) user;
           monthSalary.setText(CommonTasks.intOrDouble(receptionist.getSalary()));

        }
    }





    public void edit() {
        CommonTasks.loadPage("receptionist/dashboard/profile/edit-info-view.fxml", editInfo);
    }

    public void back() {
        CommonTasks.pageNavigate("receptionist/dashboard/receptionist-dashboard.fxml");
    }
}
