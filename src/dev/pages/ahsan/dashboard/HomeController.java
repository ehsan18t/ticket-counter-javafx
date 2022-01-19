package dev.pages.ahsan.dashboard;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;

public class HomeController implements Initializable {

    @FXML
    private AnchorPane btnAdmin;

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Text txtTitle;

    @FXML
    private Text txtUserName;

    @FXML
    private ImageView btnLogout;

    @FXML
    private ImageView btnMenu;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane mainPaneHome;

    @FXML
    private AnchorPane btnSettings;

    Image image1;
    Image image2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/017-menu-6.png")));

        // set
        menuPane.setVisible(false);
        txtTitle.setText(Config.title + " " + Config.version);

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnSettings.setOnMouseClicked(this::btnSettingsAction);
        btnAdmin.setOnMouseClicked(this::btnAdminAction);

        System.out.println(" - Logged in as " + Main.user.getName());
        txtUserName.setText(Main.user.getName() + "");

        btnAdmin.setVisible(Main.user.getType().equals("Admin"));
    }

    private void btnAdminAction(MouseEvent mouseEvent) {
        try {
            Parent admin = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.adminScene)));
            Main.screenController.addScreen("Admin", 646, 1051, admin);
            Main.screenController.activate("Admin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void btnSettingsAction(MouseEvent mouseEvent) {
        try {
            Parent settings = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.settingsScene)));
            Main.screenController.addScreen("Settings", 646, 1051, settings);
            Main.screenController.activate("Settings");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void btnMenuAction(MouseEvent mouseEvent) {
        if (menuPane.isVisible()) {
            btnMenu.setImage(image2);
            new FadeIn(btnMenu).play();
            new SlideOutLeft(menuPane).play();
            Timer t = new java.util.Timer();
            t.schedule(new java.util.TimerTask() {
                @Override
                public void run() {
                    new Thread(() -> {
                        mainPaneHome.setEffect(null);
                        menuPane.setVisible(false);
                        t.cancel();
                    }).start();
                }
            }, 500);
        } else {
            btnMenu.setImage(image1);
            new FadeIn(btnMenu).play();
            menuPane.setVisible(true);
            new SlideInLeft(menuPane).play();
            mainPaneHome.setEffect(new GaussianBlur(40));
        }
    }

    private void btnLogoutAction(MouseEvent mouseEvent) {
        if (Utils.removeFile(Config.userTempData)) {
            Utils.removeFile(Config.savedUserData);
            System.out.println(" - Logout Successful!");
            Main.screenController.activate("Login");
        } else
            System.out.println(" - Unexpected error on Logout Button Action!");
    }

    private void setBtnCloseAction(MouseEvent event) {
        Utils.exit();
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
