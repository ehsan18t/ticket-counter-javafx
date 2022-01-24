package dev.pages.ahsan.registration;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.User;
import dev.pages.ahsan.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RegistrationController  implements Initializable {

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Button btnRegister;

    @FXML
    private Button btnSignIn;

    @FXML
    private Text errorMsg;

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfPass1;

    @FXML
    private TextField tfPass2;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfUserName;

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
        btnSignIn.setOnAction(this::btnSignInAction);
        tglTheme.setOnAction(this::tglThemeOnClick);
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnRegister.setOnAction(this::btnRegisterAction);
    }

    private void btnRegisterAction(ActionEvent actionEvent) {
        try {
            ObjectOutputStream sendObj = Main.sendObj;
            ObjectInputStream receiveObj = Main.receiveObj;

            // sending registration data
            System.out.println(" - Sending credentials");
            System.out.println(" - Requesting for registration");
            sendObj.writeObject("registration");
            if (tfPass1.getText().equals(tfPass2.getText())) {
                User user = new User(tfUserName.getText(), tfEmail.getText(), tfPhone.getText(), Utils.sha256(tfPass1.getText()));
//                user.setType("Admin");
                sendObj.writeObject(user);

                // reading response
                String response = (String) receiveObj.readObject();
                System.out.println(" - Received response: " + response);
                if (response.contains("SUCCESS")) {
                    System.out.println(" - Registration Successful!");
                    Main.screenController.activate("Login");
                } else
                    errorMsg.setText("User with same email already exist!");
            } else
                errorMsg.setText("Confirm Password doesn't match!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void btnSignInAction(ActionEvent actionEvent) {
        Main.screenController.activate("Login");
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
        Utils.exit();
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}