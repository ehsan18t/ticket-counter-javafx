package dev.pages.ahsan.buy;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.Bus;
import dev.pages.ahsan.user.Ticket;
import dev.pages.ahsan.utils.KeyValuePair;
import dev.pages.ahsan.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
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
    private Button btnBuy;

    @FXML
    private ChoiceBox<KeyValuePair> choiceBus;

    @FXML
    private ToggleButton btnSeat_0_0;

    @FXML
    private ToggleButton btnSeat_0_1;

    @FXML
    private ToggleButton btnSeat_0_2;

    @FXML
    private ToggleButton btnSeat_0_3;

    @FXML
    private ToggleButton btnSeat_1_0;

    @FXML
    private ToggleButton btnSeat_1_1;

    @FXML
    private ToggleButton btnSeat_1_2;

    @FXML
    private ToggleButton btnSeat_1_3;

    @FXML
    private ToggleButton btnSeat_2_0;

    @FXML
    private ToggleButton btnSeat_2_1;

    @FXML
    private ToggleButton btnSeat_2_2;

    @FXML
    private ToggleButton btnSeat_2_3;

    @FXML
    private ToggleButton btnSeat_3_0;

    @FXML
    private ToggleButton btnSeat_3_1;

    @FXML
    private ToggleButton btnSeat_3_2;

    @FXML
    private ToggleButton btnSeat_3_3;

    @FXML
    private ToggleButton btnSeat_4_0;

    @FXML
    private ToggleButton btnSeat_4_1;

    @FXML
    private ToggleButton btnSeat_4_2;

    @FXML
    private ToggleButton btnSeat_4_3;

    @FXML
    private ToggleButton btnSeat_5_0;

    @FXML
    private ToggleButton btnSeat_5_1;

    @FXML
    private ToggleButton btnSeat_5_2;

    @FXML
    private ToggleButton btnSeat_5_3;

    @FXML
    private ToggleButton btnSeat_6_0;

    @FXML
    private ToggleButton btnSeat_6_1;

    @FXML
    private ToggleButton btnSeat_6_2;

    @FXML
    private ToggleButton btnSeat_6_3;

    @FXML
    private ArrayList<ToggleButton> seats;

    Image image1;
    Image image2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seats = new ArrayList<>();
        initBtn();
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
        btnBuy.setOnAction(this::btnBuyAction);

        addValues();
    }

    private void btnBuyAction(ActionEvent actionEvent) {
    }

    void addValues() {
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mm a").toFormatter(Locale.ENGLISH);
        LocalTime ct = LocalTime.now();
        LocalDate cd = LocalDate.now();
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry: Main.busData.entrySet()) {
            LocalTime t  = LocalTime.parse(entry.getKey().getTime(), timeFormatter);
            if (cd.isAfter(entry.getKey().getDate()))
                continue;
            if (cd.isEqual(entry.getKey().getDate()))
                if (ct.isAfter(t))
                    continue;

            String value = entry.getKey().getId() + ". " + entry.getKey().getFrom() + " - " + entry.getKey().getTo() + " [" + entry.getKey().getDate() + " - " + entry.getKey().getTime() + "]";
            choiceBus.getItems().add(new KeyValuePair(entry.getKey(), value));
        }
    }

    private void initBtn() {
        seats.add(btnSeat_0_0);
        seats.add(btnSeat_0_1);
        seats.add(btnSeat_0_2);
        seats.add(btnSeat_0_3);

        seats.add(btnSeat_1_0);
        seats.add(btnSeat_1_1);
        seats.add(btnSeat_1_2);
        seats.add(btnSeat_1_3);

        seats.add(btnSeat_2_0);
        seats.add(btnSeat_2_1);
        seats.add(btnSeat_2_2);
        seats.add(btnSeat_2_3);

        seats.add(btnSeat_3_0);
        seats.add(btnSeat_3_1);
        seats.add(btnSeat_3_2);
        seats.add(btnSeat_3_3);

        seats.add(btnSeat_4_0);
        seats.add(btnSeat_4_1);
        seats.add(btnSeat_4_2);
        seats.add(btnSeat_4_3);

        seats.add(btnSeat_5_0);
        seats.add(btnSeat_5_1);
        seats.add(btnSeat_5_2);
        seats.add(btnSeat_5_3);

        seats.add(btnSeat_6_0);
        seats.add(btnSeat_6_1);
        seats.add(btnSeat_6_2);
        seats.add(btnSeat_6_3);
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
