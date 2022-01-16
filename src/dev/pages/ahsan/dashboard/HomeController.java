package dev.pages.ahsan.dashboard;

import dev.pages.ahsan.main.Config;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ImageView btnClose;

    @FXML
    private ImageView btnMin;

    @FXML
    private Text txtTitle;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set
        txtTitle.setText(Config.title + " " + Config.version);

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
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
}
