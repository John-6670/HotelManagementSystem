package application.hotelmanagementsystem;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class CommonTasks extends Main {
    public static void pageNavigate(String dest) {
        double xTemp = x;
        double yTemp = y;

        if (stage == null) {
            xTemp = x + (400);
            yTemp = y + (500/7.0);
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
        }

        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(dest)));
            preparePage(root);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object loadPage(String dest, Pane parent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(dest));
            Pane child = loader.load();
            parent.getChildren().clear();
            parent.getChildren().add(child);
            return loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setOnlyNumber(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d+")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
