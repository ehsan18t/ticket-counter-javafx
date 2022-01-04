package dev.pages.ahsan.login;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
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
    private CheckBox chboxRemember;

    @FXML
    private TextField tfPass;

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
        btnRegister.setOnAction(this::btnRegisterAction);
        tglTheme.setOnAction(this::tglThemeOnClick);
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
    }


    public void tglThemeOnClick(ActionEvent e) {
        if (!tglTheme.isSelected())
            selectDarkTheme();
        else
            selectLightTheme();
        Main.root.getStylesheets().add(Objects.requireNonNull(getClass().getResource(Config.CSS)).toExternalForm());
    }

    private void selectLightTheme() {
        tglTheme.setSelected(false);
        Utils.changeCSS(Main.root, Config.lightCSS);
    }

    public void selectDarkTheme() {
        tglTheme.setSelected(true);
        Utils.changeCSS(Main.root, Config.darkCSS);
    }

    private void setBtnCloseAction(MouseEvent event) {
        System.exit(0);
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    private void btnRegisterAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.registrationScene)));
            Scene scene = new Scene(root);
            Utils.makeDraggable(scene);
            Main.primaryStage.setScene(scene);
            Main.primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
