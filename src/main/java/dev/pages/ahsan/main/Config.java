package dev.pages.ahsan.main;

public class Config {
    // App Settings
    public static final String title = "Ticket Counter";
    public static final String version = "v1.0";
    public static final String server = "localhost";
    public static final int port = 6611;
    public static final String userTempData = "userData.ser";
    public static final String savedUserData = "savedUser.ser";
    public static final double defaultHeight = 646;
    public static final double defaultWeight = 1051;

    // Pages
    public static final String loginScene = "/dev/pages/ahsan/login/login.fxml";
    public static final String registrationScene = "/dev/pages/ahsan/registration/registration.fxml";
    public static final String homeScene = "/dev/pages/ahsan/dashboard/home.fxml";
    public static final String settingsScene = "/dev/pages/ahsan/settings/Settings.fxml";
    public static final String adminScene = "/dev/pages/ahsan/admin/Admin.fxml";
    public static final String buyScene = "/dev/pages/ahsan/buy/Buy.fxml";
    public static final String aboutScene = "/dev/pages/ahsan/about/About.fxml";
    public static final String lightCSS = "/res/css/light.css";
    public static final String darkCSS = "/res/css/dark.css";
    public static String CSS = lightCSS;
    public static final String icon = "/res/img/icon.png";
    public static final String exitIcon = "/res/img/exit.png";
    public static final String minimizeIcon = "/res/img/minimize.png";

    // Info
    public static final String author = "Md. Ahasan Khan";
    public static final String email = "mkhan201122@bscse.uiu.ac.bd";
    public static final String org = "United International University";
    public static final String gitProfile = "https://github.com/Ahsan40";
    public static final String gitRepo = gitProfile + "/ticket-counter-javafx";
}
