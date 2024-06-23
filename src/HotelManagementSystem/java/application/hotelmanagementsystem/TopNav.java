package application.hotelmanagementsystem;

import java.io.IOException;

public abstract class TopNav extends CloseButton {
    public void logout() throws IOException {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }

    public abstract void showProfile();
    public abstract void showFinance();
}
