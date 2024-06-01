package application.hotelmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static double x, y;
    public static double xxx, yyy;

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("welcome-view.fxml")));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Hotel Management System");
        Scene scene = new Scene(root, 800, 500);
        stage.setScene(scene);
        stage.setX(380);
        stage.setY(200);

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
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}