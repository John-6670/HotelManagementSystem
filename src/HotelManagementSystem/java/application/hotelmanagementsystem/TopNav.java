package application.hotelmanagementsystem;

import java.io.IOException;

public abstract class TopNav extends CloseButton {
    public void logout() {
        CommonTasks.pageNavigate("welcome-view.fxml");
    }

    public abstract void showProfile() throws IOException;
    public abstract void showFinance();
}
