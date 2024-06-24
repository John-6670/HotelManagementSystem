package application.hotelmanagementsystem;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
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

    public static void loadPopUp(String dest) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource(dest));
            Stage newStage = new Stage();
            newStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root, 400, 200);
            newStage.setScene(scene);
            newStage.setX(580);
            newStage.setY(370);
            newStage.show();
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

    public static void showConfirmation(String text) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("alerts/confirm-alert-view.fxml"));
            Stage newStage = new Stage();

            Text alertText = (Text) root.lookup("#alertContent");
            alertText.setText(text);

            JFXButton confirmButton = (JFXButton) root.lookup("#confirm");
            JFXButton cancelButton = (JFXButton) root.lookup("#cancel");

            confirmButton.setOnAction(event -> {
                newStage.close();
            });
            cancelButton.setOnAction(event -> {
                newStage.close();
            });

            newStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root, 400, 170);
            newStage.setScene(scene);
            newStage.setX(580);
            newStage.setY(370);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showError(String text) {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("alerts/error-alert-view.fxml"));
            Stage newStage = new Stage();

            Text errorText = (Text) root.lookup("#errorContent");
            errorText.setText(text);

            JFXButton button = (JFXButton) root.lookup("#ok");

            button.setOnAction(event -> {
                newStage.close();
            });

            newStage.initStyle(StageStyle.UNDECORATED);
            Scene scene = new Scene(root, 300, 100);
            newStage.setScene(scene);
            newStage.setX(630);
            newStage.setY(400);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
