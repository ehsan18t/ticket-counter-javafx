package dev.pages.ahsan.main;

import dev.pages.ahsan.utils.ScreenController;
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
    public static Scene scene;
    public static ScreenController screenController;

    @Override
    public void start(Stage stage) throws Exception{

        primaryStage = stage;

        // Set FXML
        Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.loginScene)));
        Parent register = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.registrationScene)));

        // scene
        scene = new Scene(login);
        screenController = new ScreenController(scene);
        screenController.addScreen("Login", login);
        screenController.addScreen("Register", register);
        screenController.activate("Login");

        // Set Title
        primaryStage.setTitle(Config.title + " " + Config.version);

        // Set CSS
        screenController.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource(Config.CSS)).toExternalForm());

        // Make Stage Transparent
        primaryStage.initStyle(StageStyle.TRANSPARENT);

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
