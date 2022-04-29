package dev.pages.ahsan.utils;

import dev.pages.ahsan.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

class Size {
    double h;
    double w;

    public Size(double h, double w) {
        this.h = h;
        this.w = w;
    }
}

public class ScreenController {
    private final HashMap<String, String> fxml = new HashMap<>();
    private final HashMap<String, Size> screenSize = new HashMap<>();
    private final HashMap<String, Parent> screenMap = new HashMap<>();
    private final Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, Parent root){
        screenMap.put(name, root);
    }

    public void addScreen(String name, double h, double w, Parent root){
        screenSize.put(name, new Size(h, w));
        screenMap.put(name, root);
    }

    public void addScreen(String name, String fxml, double h, double w){
        Parent root = null;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(fxml)));
            screenSize.put(name, new Size(h, w));
            screenMap.put(name, root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        if (screenSize.containsKey(name)) {
            Size s = screenSize.get(name);
            Main.primaryStage.setWidth(s.w);
            Main.primaryStage.setHeight(s.h);
        }
        main.setRoot(screenMap.get(name));
    }

    public void activate(String name, double height, double width){
        Main.primaryStage.setWidth(width);
        Main.primaryStage.setHeight(height);
        main.setRoot(screenMap.get(name));
    }

    public Parent getRoot(String key) {
        return screenMap.get(key);
    }

    public Parent getRoot() {
        return main.getRoot();
    }
    public Scene getScene() {
        return main;
    }
}