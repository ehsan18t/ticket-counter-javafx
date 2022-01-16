package dev.pages.ahsan.login;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.User;
import dev.pages.ahsan.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Button btnSignIn;

    @FXML
    private Button btnRegister;

    @FXML
    private CheckBox chkRememberMe;

    @FXML
    private TextField tfPass;

    @FXML
    private TextField tfUserName;

    @FXML
    private ToggleButton tglTheme;

    @FXML
    private Text txtTitle;

    @FXML
    private Text errorMsg;

    private InputStream is;
    private ObjectInputStream receiveObj;

    private OutputStream oo;
    private ObjectOutputStream sendObj;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // init
        tglTheme = new ToggleButton();
        tglTheme.setSelected(false);

        // set
        txtTitle.setText(Config.title + " " + Config.version);

        // Action Event
        btnRegister.setOnAction(this::btnRegisterAction);
        tglTheme.setOnAction(this::tglThemeOnClick);
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnSignIn.setOnAction(this::btnSignInAction);
    }

    private void btnSignInAction(ActionEvent actionEvent) {
        boolean result = Utils.checkLogin(new User(tfUserName.getText(), Utils.sha256(tfPass.getText())), Main.receiveObj, Main.sendObj);
        if (result) {
            if (chkRememberMe.isSelected())
                Utils.writeUserToFile(Utils.readUserFromFile(Config.userTempData), Config.savedUserData);
            System.out.println(" - Logging in to User Control Panel");
            Main.screenController.activate("Home");
        } else {
            errorMsg.setText("Login Failed!");
        }
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
        File f = new File("userData.ser");
        if (f.delete())
            System.out.println("Deleted temp user data userData.ser");
        System.exit(0);
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private void btnRegisterAction(ActionEvent actionEvent) {
        Main.screenController.activate("Register");
    }
}
