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
    public static void pageNavigate(String dest) throws IOException {
        double xTemp = x;
        double yTemp = y;

        if (stage == null) {
            xTemp = x + (400);
            yTemp = y + (500/7.0);
            stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
        }

        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(dest)));

        preparePage(root);
        stage.show();
    }

    public static Object loadPage(String dest, Pane parent) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(dest));
            Pane child = loader.load();
            if (parent instanceof BorderPane) {
                BorderPane pane = (BorderPane) ((BorderPane) parent.getParent()).getRight();
                if (pane != null) {
                    pane.getChildren().clear();
                }
            }
            parent.getChildren().clear();
            parent.getChildren().add(child);
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
