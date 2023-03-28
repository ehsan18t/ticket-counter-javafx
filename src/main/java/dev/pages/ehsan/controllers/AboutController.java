package dev.pages.ehsan.controllers;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ehsan.main.Config;
import dev.pages.ehsan.main.Main;
import dev.pages.ehsan.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;

public class AboutController implements Initializable {

    Image image1;
    Image image2;
    // About Info
    @FXML
    private Text txtTitle;
    @FXML
    private Text txtAuthorName;
    @FXML
    private Hyperlink txtAuthorEmail;
    @FXML
    private Text txtOrgName;
    @FXML
    private Hyperlink txtRepo;
    // TopBar
    @FXML
    private ImageView btnClose;
    @FXML
    private ImageView btnMin;
    // Logged User Details
    @FXML
    private Text txtUserName;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtPhone;
    // Manu & Others
    @FXML
    private ImageView btnLogout;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane mainPaneHome;
    @FXML
    private ImageView btnMenu;
    @FXML
    private AnchorPane btnBuy;
    @FXML
    private AnchorPane btnSettings;
    @FXML
    private AnchorPane btnHome;
    @FXML
    private AnchorPane btnAdmin;
    @FXML
    private Text txtBuy;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/017-menu-6.png")));

        // About Info
        txtTitle.setText(Config.title + " " + Config.version);
        txtAuthorName.setText(Config.author);
        txtOrgName.setText(Config.org);
        txtAuthorEmail.setText(Config.email);
        txtAuthorEmail.setOnMouseClicked(e -> Utils.openLink("mailto:" + Config.email));
        txtRepo.setText(Config.gitRepo);
        txtRepo.setOnMouseClicked(e -> Utils.openLink(Config.gitRepo));

        // set
        menuPane.setVisible(false);
        btnAdmin.setVisible(false);
        txtUserName.setText(Main.user.getName() + "");
        txtEmail.setText(Main.user.getEmail() + "");
        txtPhone.setText(Main.user.getPhone() + "");

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);

        // Manu Action
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnBuy.setOnMouseClicked(this::btnBuyAction);
        btnSettings.setOnMouseClicked(this::btnSettingsAction);
        btnHome.setOnMouseClicked(this::btnHomeAction);
        btnAdmin.setOnMouseClicked(this::btnAdminAction);

        txtUserName.setText(Main.user.getName() + "");

        if (Main.user.getType().equals("Admin")) {
            btnAdmin.setVisible(true);
            btnHome.setVisible(false);
            txtBuy.setText("History");
        }
    }

    private void btnHomeAction(MouseEvent mouseEvent) {
        Main.sceneMan.open("home", Config.homeScene);
    }

    private void btnBuyAction(MouseEvent mouseEvent) {
        Main.sceneMan.open("buy", Config.buyScene);
    }

    private void btnAdminAction(MouseEvent mouseEvent) {
        Main.sceneMan.open("admin", Config.adminScene);
    }

    public void btnSettingsAction(MouseEvent mouseEvent) {
        Main.sceneMan.open("settings", Config.settingsScene);
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
        if (Utils.removeFile(Config.userTempData) && Utils.removeFile(Config.savedUserData)) {
            System.out.println(" - Logout Successful!");
        }
        Main.sceneMan.reload("login");
        Main.sceneMan.activate("login");
    }

    private void setBtnCloseAction(MouseEvent event) {
        Utils.exit();
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
