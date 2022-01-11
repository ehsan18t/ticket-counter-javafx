package dev.pages.ahsan.utils;

import dev.pages.ahsan.main.Main;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.HashMap;

public class ScreenController {
    private final HashMap<String, Parent> screenMap = new HashMap<>();
    private final Scene main;

    public ScreenController(Scene main) {
        this.main = main;
    }

    public void addScreen(String name, Parent root){
        screenMap.put(name, root);
    }

    public void removeScreen(String name){
        screenMap.remove(name);
    }

    public void activate(String name){
        main.setRoot(screenMap.get(name) );
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