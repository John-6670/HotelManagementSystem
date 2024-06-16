package application.hotelmanagementsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AdminDashboard {
    @FXML
    private AnchorPane content;

    // Methods to handle button clicks from navbar (replace with your logic)
    public void handleHomeClick() {
        loadFxml("AdminDashboard/home.fxml"); // Replace with the FXML file for the home view
    }

    public void handleNav1Click() {
        loadFxml("AdminDashboard/test.fxml");
    }

    // Method to load FXML files dynamically
    private void loadFxml(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Pane newContent = loader.load();
            content.getChildren().setAll(newContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
