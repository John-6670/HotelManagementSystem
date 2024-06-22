package application.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class GuestDashboard extends CloseButton {
    @FXML
    private BorderPane content;

    public void loadRoomPage() {
        CommonTasks.loadPage("guest/Room/room.fxml", content);
    }

    public void loadReservationPage() {
        CommonTasks.loadPage("guest/Room/reservation.fxml", content);
    }
}
