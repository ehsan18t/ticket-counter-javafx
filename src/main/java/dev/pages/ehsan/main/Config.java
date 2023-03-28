package dev.pages.ehsan.main;

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
    public static final String loginScene = "/fxml/login.fxml";
    public static final String registrationScene = "/fxml/registration.fxml";
    public static final String homeScene = "/fxml/home.fxml";
    public static final String settingsScene = "/fxml/Settings.fxml";
    public static final String adminScene = "/fxml/Admin.fxml";
    public static final String buyScene = "/fxml/Buy.fxml";
    public static final String aboutScene = "/fxml/About.fxml";

    //Resources
    public static final String lightCSS = "/css/light.css";
    public static final String darkCSS = "/css/dark.css";
    public static final String icon = "/img/icon.png";
    public static final String exitIcon = "/img/exit.png";
    public static final String minimizeIcon = "/img/minimize.png";
    // Info
    public static final String author = "Md. Ehsan Khan";
    public static final String email = "mkhan201122@bscse.uiu.ac.bd";
    public static final String org = "United International University";
    public static final String gitProfile = "https://github.com/ehsan18t";
    public static final String gitRepo = gitProfile + "/ticket-counter-javafx";
    public static String CSS = lightCSS;
}
