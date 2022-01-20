package dev.pages.ahsan.buy;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.Bus;
import dev.pages.ahsan.user.Ticket;
import dev.pages.ahsan.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class BuyController  implements Initializable {

    HashMap<Bus, HashMap<String, ArrayList<Ticket>>> buses;

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

    @FXML
    private Text txtEmail;

    @FXML
    private Text txtPhone;

    @FXML
    private ChoiceBox<HashMap<Bus, HashMap<String, ArrayList<Ticket>>>> choiceBus;

    Image image1;
    Image image2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/017-menu-6.png")));

        // set
        menuPane.setVisible(false);
        txtTitle.setText(Config.title + " " + Config.version);
        txtUserName.setText(Main.user.getName() + "");
        txtEmail.setText(Main.user.getEmail() + "");
        txtPhone.setText(Main.user.getPhone() + "");

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnSettings.setOnMouseClicked(this::btnSettingsAction);
    }

    public void btnSettingsAction(MouseEvent mouseEvent) {
        Main.screenController.activate("Settings");
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
