package dev.pages.ahsan.settings;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.User;
import dev.pages.ahsan.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

public class ChangePassController implements Initializable {
    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Text txtTitle;

    @FXML
    private Text txtUserName;

    @FXML
    private Text txtEmail;

    @FXML
    private Text txtPhone;

    @FXML
    private ImageView btnLogout;

    @FXML
    private ImageView btnMenu;

    @FXML
    private AnchorPane btnHome;

    @FXML
    private AnchorPane menuPane;

    @FXML
    private AnchorPane mainPaneHome;

    @FXML
    private Button btnSave;

    @FXML
    private TextField tfName;

    @FXML
    private Text txtError;

    @FXML
    private TextField tfNewPass;

    @FXML
    private TextField tfOldPass;

    @FXML
    private TextField tfPhone;



    Image image1;
    Image image2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/017-menu-6.png")));

        // set
        menuPane.setVisible(false);
        txtTitle.setText(Config.title + " " + Config.version);
        System.out.println(" - Logged in as " + Main.user.getName());
        setTF();

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnSave.setOnAction(this::btnSaveAction);
        btnHome.setOnMouseClicked(this::btnHomeAction);
    }

    private void btnHomeAction(MouseEvent mouseEvent) {
        Main.screenController.activate("Home");
    }

    private void btnSaveAction(ActionEvent actionEvent) {
        String pass  = Utils.sha256(tfNewPass.getText());

        User user = new User(Main.user.getName(), Main.user.getEmail(), Main.user.getPhone(), Main.user.getPasswords());

        user.setName(tfName.getText());
        user.setPhone(tfPhone.getText());

        // for only name and phone
        if (tfNewPass.getText().trim().isEmpty() && tfOldPass.getText().trim().isEmpty()) {
            pass = user.getPasswords();
            System.out.println(" [not changing pass]");
            boolean result = Utils.updateInfo(user, pass, Main.receiveObj, Main.sendObj);
            if (result) {
                System.out.println(" - Updating user info of " + Main.user.getName());
                setTF();
                txtError.setText("Settings Saved!");
            }
        } else if (!user.getPasswords().equals(pass)) {   // for passwords
            boolean result = Utils.updateInfo(user, pass, Main.receiveObj, Main.sendObj);
            System.out.println(" [changing pass]");
            if (result) {
                System.out.println(" - Updating user info of (with pass)" + Main.user.getName());
                setTF();
                txtError.setText("Settings Saved!");
            } else {
                txtError.setText("Wrong Passwords!");
            }
        } else {
            txtError.setText("New passwords can not be same as old passwords!");
        }
    }

    private void setTF() {
        txtUserName.setText(Main.user.getName() + "");
        txtEmail.setText(Main.user.getEmail() + "");
        txtPhone.setText(Main.user.getPhone() + "");
        tfName.setText(Main.user.getName() + "");
        tfPhone.setText(Main.user.getPhone() + "");
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
