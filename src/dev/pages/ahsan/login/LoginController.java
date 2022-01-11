package dev.pages.ahsan.login;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.User;
import dev.pages.ahsan.user.WriteUser;
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
import java.util.concurrent.atomic.AtomicReference;

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
    private CheckBox chboxRemember;

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
        try {
            Socket sc = new Socket("localhost", 6600);
            OutputStream oo = sc.getOutputStream();
            ObjectOutputStream sendObj = new ObjectOutputStream(oo);

            InputStream inputStream = sc.getInputStream();
            ObjectInputStream receiveObj = new ObjectInputStream(inputStream);

            // sending credentials
            System.out.println(" - Sending credentials");
            System.out.println(" - Requesting for login");
            sendObj.writeObject("login");
            sendObj.writeObject(new User(tfUserName.getText(), Utils.sha256(tfPass.getText())));

            // reading response
            String response = (String) receiveObj.readObject();
            System.out.println(" - Received response: " + response);
            if (response.contains("SUCCESS")) {
                System.out.println(" - Received logged user info from server");
                User user = (User) receiveObj.readObject();
                System.out.println(" - Saving user info for later use");
                Utils.writeUserToFile(user, "userData.ser");    // writing info to a temp file
                System.out.println(" - Logging in to User Control Panel");
                Main.screenController.activate("Register", 656, 500);
            } else {
                errorMsg.setText("Login Failed!");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        System.exit(0);
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private void btnRegisterAction(ActionEvent actionEvent) {
        Main.screenController.activate("Register", 656, 500);
    }
}
