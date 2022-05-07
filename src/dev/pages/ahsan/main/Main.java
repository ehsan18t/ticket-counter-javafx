package dev.pages.ahsan.main;

import dev.pages.ahsan.user.Bus;
import dev.pages.ahsan.user.Ticket;
import dev.pages.ahsan.user.User;
import dev.pages.ahsan.utils.SceneManager;
import dev.pages.ahsan.utils.Size;
import dev.pages.ahsan.utils.Utils;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Application {
    public static HashMap<Bus, HashMap<String, ArrayList<Ticket>>>  busData;
    public static User user = null;
    public static SceneManager sceneMan;
    public static Socket sc;
    public static ObjectOutputStream sendObj;
    public static ObjectInputStream receiveObj;

    @Override
    public void start(Stage stage) throws Exception{
        // Scene Configs
        stage.setTitle(Config.title + " " + Config.version);
        stage.initStyle(StageStyle.TRANSPARENT);
        sceneMan = new SceneManager(stage, Config.lightCSS);
        sceneMan.setDefaultSize(new Size(Config.defaultHeight, Config.defaultWeight));

        // Configure
        sc = new Socket(Config.server, Config.port);
        OutputStream oo = sc.getOutputStream();
        sendObj = new ObjectOutputStream(oo);

        InputStream inputStream = sc.getInputStream();
        receiveObj = new ObjectInputStream(inputStream);

        File f1 = new File(Config.savedUserData);
        File f2 = new File(Config.userTempData);

        if (f1.exists()) {
            Files.copy(f1.toPath(), f2.toPath());
            while (!f2.canWrite()) {
                System.out.println(" - Writing File");
            }
        }


        // Determining Page to Open
        System.out.println(" - Checking User");
        sceneMan.add("login", Config.loginScene, 600, 500);
        sceneMan.add("registration", Config.registrationScene,656, 500);
        if (f2.exists()) {
            boolean result = Utils.checkLogin(Utils.readUserFromFile(Config.userTempData), receiveObj, sendObj);
            if (result) {
                if (Main.user.getType().equals("Admin")) {
                    sceneMan.open("admin", Config.adminScene);
                } else {
                    System.out.println(" - Logging in to User Control Panel.");
                    sceneMan.open("home", Config.homeScene);
                }
            }
        } else {
            System.out.println(" - Redirecting to Login Page.");
            sceneMan.activate("login");
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void stop() throws Exception {
        super.stop(); //To change body of generated methods, choose Tools | Templates.
        Utils.exit();
    }
}
