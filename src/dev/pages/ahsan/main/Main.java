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

import java.io.File;
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
        Parent home = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.homeScene)));

        // scene
        scene = new Scene(login);
        screenController = new ScreenController(scene);
        screenController.addScreen("Login", 600, 500, login);
        screenController.addScreen("Register", 656, 500, register);
        screenController.addScreen("Home", 646, 1051, home);


        // Determining Page to Open
        System.out.println(" - checking User");
        File f = new File("savedUser.ser");
        if (f.exists()) {
            System.out.println(" - Saved User Data found!");
            System.out.println(" - Login Using Saved Data!");
            screenController.activate("Home");
        }
        else
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

    @Override
    public void stop() throws Exception {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
        File f = new File("userData.ser");
        if (f.delete())
            System.out.println("Deleted temp user data userData.ser");
        System.exit(0);
    }
}
