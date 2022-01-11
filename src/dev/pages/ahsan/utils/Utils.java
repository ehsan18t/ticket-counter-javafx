package dev.pages.ahsan.utils;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.User;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

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

    public static String sha256(final String base) {
        try{
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final byte[] hash = digest.digest(base.getBytes(StandardCharsets.UTF_8));
            final StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                final String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static void writeUserToFile(User user, String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
            System.out.println(" - Serialized data is saved in " + filePath);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
