package dev.pages.ahsan.utils;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.User;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
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

    public static User readUserFromFile(String filePath) {
        User user = null;
        try {
            FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            user = (User) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return user;
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

    public static boolean checkLogin(User userData, ObjectInputStream receiveObj, ObjectOutputStream sendObj) {
        try {
            // sending credentials
            System.out.println(" - Sending credentials");
            System.out.println(" - Requesting for login");
            sendObj.writeObject("login");
            sendObj.writeObject(userData);

            // reading response
            String response = (String) receiveObj.readObject();
            System.out.println(" - Received response: " + response);
            if (response.contains("SUCCESS")) {
                System.out.println(" - Received logged user info from server");
                Main.user = (User) receiveObj.readObject();
                System.out.println(" - Saving user info for later use");
                Utils.writeUserToFile(Main.user, Config.userTempData);    // writing info to a temp file
                return true;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean updateInfo(User userData, String newPass, ObjectInputStream receiveObj, ObjectOutputStream sendObj) {
        try {
            // checking credentials
            System.out.println(" - Sending credentials");
            System.out.println(" - Requesting for check user");
            String response = null;
            if (userData.getPasswords().equals(Main.user.getPasswords()))
                response = "SUCCESS";

            // reading response
            System.out.println(" - Received response: " + response);

            // updating info
            if (response != null) {
                System.out.println(" - Requesting for update user data");

                userData.setPasswords(newPass);
                System.out.println(userData.getPasswords());

                // sending data
                sendObj.writeObject("updateInfo");
                sendObj.writeObject(userData);

                System.out.println(" - Received new user info from server");
                Main.user = userData;
                Utils.writeUserToFile(Main.user, Config.userTempData);    // writing info to a temp file
                System.out.println(" - Updating new info");
                File f  = new File(Config.savedUserData);
                if (f.exists())
                    writeUserToFile(Main.user, Config.savedUserData);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeFile(String path) {
        File f = new File(path);
        return f.delete();
    }


    public static void exit() {
        if (removeFile(Config.userTempData))
            System.out.println(" - Deleted temp user data " + Config.userTempData);
        System.exit(0);
    }

   public static void openLink(String link) {
        try {
            Desktop.getDesktop().browse(new URI(link));
        } catch (URISyntaxException | IOException ex) {
            System.err.println(" - Link open failed!");
        }
    }
}
