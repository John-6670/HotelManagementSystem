package application.hotelmanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    public static double x, y;
    public static double xxx, yyy;
    public static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Main.stage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource("welcome-view.fxml")));
        stage.initStyle(StageStyle.UNDECORATED);
        preparePage(root);
        stage.setTitle("Welcome Page");
        stage.show();
    }

    public static Stage preparePage(Parent root) {
        Scene scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
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

        return stage;
    }

    public static void main(String[] args) {
        launch();
    }
}