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

    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("login-view.fxml"));
        Scene scene = new Scene(root,300,275);
        stage.setScene(scene);
        stage.setTitle("Login");
        scene.getStylesheets().add(getClass().getResource("Login.css").toExternalForm());
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}