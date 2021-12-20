package dev.pages.ahsan.utils;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Objects;

public class Utils {
    private static double xOffset;
    private static double yOffset;

    public static void makeDraggable(Scene scene) {
        // Make Scene Draggable
        scene.setOnMousePressed(event -> {
            xOffset = Main.primaryStage.getX() - event.getScreenX();
            yOffset = Main.primaryStage.getY() - event.getScreenY();
        });
        scene.setOnMouseDragged(event -> {
            Main.primaryStage.setX(event.getScreenX() + xOffset);
            Main.primaryStage.setY(event.getScreenY() + yOffset);
        });
    }

    public static void changeCSS(Parent root, String css) {
        root.getStylesheets().clear();
        Config.CSS = css;
        root.getStylesheets().add(Config.CSS);
    }
}
