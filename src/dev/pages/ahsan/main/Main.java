package dev.pages.ahsan.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception{
        primaryStage = stage;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/dev/pages/ahsan/login/loginScene.fxml")));
        primaryStage.setTitle(Config.title + " " + Config.version);
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Config.CSS)).toExternalForm());
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
