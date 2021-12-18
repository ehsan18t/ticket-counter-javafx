package dev.pages.ahsan.main;

import dev.pages.ahsan.utils.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {
    public static Stage primaryStage;
    public static Parent root;

    @Override
    public void start(Stage stage) throws Exception{
        Scene scene;

        // Set FXML
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.loginScene)));
        primaryStage = stage;

        // Set Title
        primaryStage.setTitle(Config.title + " " + Config.version);

        // Set CSS
        root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Config.CSS)).toExternalForm());

        // Make Stage Transparent
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        scene = new Scene(root);

        // Make Scene Draggable
        Utils.makeDraggable(scene);

        // Make Scene Transparent
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
