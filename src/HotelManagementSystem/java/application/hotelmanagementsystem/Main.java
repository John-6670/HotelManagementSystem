package application.hotelmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static double x, y;
    public static double xxx, yyy;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        Parent root = FXMLLoader.load(Main.class.getResource("welcome-view.fxml"));
        stage.initStyle(StageStyle.UNDECORATED);
        preparePage(root);
        stage.show();
    }

    public static void preparePage(Parent root) {
        Scene scene = new Scene(root, 1000, 600);
        stage.setScene(scene);
        stage.setX(280);
        stage.setY(170);

        root.setOnMousePressed(event -> {
            xxx = event.getSceneX();
            yyy = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xxx);
            stage.setY(event.getScreenY() - yyy);
            x = stage.getX();
            y = stage.getY();
        });
    }

    public static void main(String[] args) {
        launch();
    }
}