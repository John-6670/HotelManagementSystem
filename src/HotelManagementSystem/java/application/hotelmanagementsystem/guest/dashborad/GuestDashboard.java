package application.hotelmanagementsystem.guest.dashborad;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.CommonTasks;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class GuestDashboard extends CloseButton {
    @FXML
    private BorderPane content;

    public void loadRoomPage() {
        CommonTasks.loadPage("guest/dashboard/room.fxml", content);
    }

    public void loadReservationPage() {
        CommonTasks.loadPage("guest/dashboard/reservation.fxml", content);
    }
}
