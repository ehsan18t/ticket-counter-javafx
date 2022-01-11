package dev.pages.ahsan.registration;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.User;
import dev.pages.ahsan.user.WriteUser;
import dev.pages.ahsan.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController  implements Initializable {

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Button btnSignIn;

    @FXML
    private CheckBox chboxRemember;

    @FXML
    private TextField tfPass;

    @FXML
    private TextField tfUserName;

    @FXML
    private Button btnRegister;

    @FXML
    private ToggleButton tglTheme;

    @FXML
    private Text txtTitle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init
        tglTheme = new ToggleButton();
        tglTheme.setSelected(false);

        // set
        txtTitle.setText(Config.title + " " + Config.version);

        // Action Event
        btnSignIn.setOnAction(this::btnSigninAction);
        tglTheme.setOnAction(this::tglThemeOnClick);
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnRegister.setOnAction(this::btnRegisterAction);
    }

    private void btnRegisterAction(ActionEvent actionEvent) {
        try {
            Socket sc = new Socket("localhost", 6600);
            WriteUser wu = new WriteUser(sc);
            wu.send("registration", new User(tfUserName.getText(), Utils.sha256(tfPass.getText())));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void btnSigninAction(ActionEvent actionEvent) {
        Main.screenController.activate("Login", 600, 500);
    }

    public void tglThemeOnClick(ActionEvent e) {
        if (!tglTheme.isSelected())
            selectDarkTheme();
        else
            selectLightTheme();
        Main.screenController.getRoot().getStylesheets().add(Objects.requireNonNull(getClass().getResource(Config.CSS)).toExternalForm());
    }

    private void selectLightTheme() {
        tglTheme.setSelected(false);
        Utils.changeCSS(Main.screenController.getRoot(), Config.lightCSS);
    }

    public void selectDarkTheme() {
        tglTheme.setSelected(true);
        Utils.changeCSS(Main.screenController.getRoot(), Config.darkCSS);
    }

    private void setBtnCloseAction(MouseEvent event) {
        System.exit(0);
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}