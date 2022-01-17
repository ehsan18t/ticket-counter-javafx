package dev.pages.ahsan.dashboard;

import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.utils.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Text txtTitle;

    @FXML
    private ImageView btnLogout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set
        txtTitle.setText(Config.title + " " + Config.version);

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);
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
