package dev.pages.ahsan.admin;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.Bus;
import dev.pages.ahsan.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;

public class AdminController implements Initializable {
    HashSet<Bus> buses;

    @FXML
    private Button btnAdd;

    @FXML
    private DatePicker tfDate;

    @FXML
    private TextField tfFrom;

    @FXML
    private TextField tfId;

    @FXML
    private TextField tfTime;

    @FXML
    private TextField tfTo;

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
    private TableView<Bus> table;

    @FXML
    private TableColumn<Bus, String> timeCol;

    @FXML
    private TableColumn<Bus, String> toCol;

    @FXML
    private TableColumn<Bus, LocalDate> dateCol;

    @FXML
    private TableColumn<Bus, String> fromCol;

    @FXML
    private TableColumn<Bus, Integer> idCol;

    Image image1;
    Image image2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/017-menu-6.png")));

        // get data from server
        try {
            Main.sendObj.writeObject("getBusList");
            buses = (HashSet<Bus>) Main.receiveObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        // Table
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        table.setItems(getBus());

        // set
        menuPane.setVisible(false);
        txtTitle.setText(Config.title + " " + Config.version);

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnSettings.setOnMouseClicked(this::btnSettingsAction);
        btnAdd.setOnAction(this::btnAddAction);

        System.out.println(" - Logged in as " + Main.user.getName());
        txtUserName.setText(Main.user.getName() + "");
    }

    private void btnAddAction(ActionEvent actionEvent) {
        try {
            Main.sendObj.writeObject("addBus");
        System.out.println("Adding");
            Bus bus = new Bus(Integer.parseInt(tfId.getText()), tfFrom.getText(), tfTo.getText(), tfDate.getValue(), tfTime.getText());
            Main.sendObj.writeObject(bus);
            buses.add(bus);
            table.getItems().clear();
            table.setItems(getBus());
            table.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get add buses
    public ObservableList<Bus> getBus() {
        ObservableList<Bus> allBus = FXCollections.observableArrayList();
        allBus.addAll(buses);
        return allBus;
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
