package dev.pages.ahsan.admin;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ahsan.main.Config;
import dev.pages.ahsan.main.Main;
import dev.pages.ahsan.user.Bus;
import dev.pages.ahsan.user.Ticket;
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
import java.util.*;

public class AdminController implements Initializable {
    HashMap<Bus, HashMap<String, ArrayList<Ticket>>> buses;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnRemove;

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
    private AnchorPane menuPane;

    @FXML
    private AnchorPane mainPaneHome;

    @FXML
    private ImageView btnMenu;

    @FXML
    private AnchorPane btnBuy;

    @FXML
    private AnchorPane btnSettings;

    @FXML
    private AnchorPane btnAbout;

    @FXML
    private AnchorPane btnHome;

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

    @FXML
    private Text txtEmail;

    @FXML
    private Text txtPhone;

    Image image1;
    Image image2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/017-menu-6.png")));

        // get data from server
        try {
            Main.sendObj.writeObject("getBusData");
            Main.busData = (HashMap<Bus, HashMap<String, ArrayList<Ticket>>>) Main.receiveObj.readObject();
            buses = Main.busData;
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
        txtUserName.setText(Main.user.getName() + "");
        txtEmail.setText(Main.user.getEmail() + "");
        txtPhone.setText(Main.user.getPhone() + "");

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);
        btnAdd.setOnAction(this::btnAddAction);
        btnRemove.setOnAction(this::btnRemoveAction);

        // Manu Action
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnBuy.setOnMouseClicked(this::btnBuyAction);
        btnSettings.setOnMouseClicked(this::btnSettingsAction);
        btnAbout.setOnMouseClicked(this::btnAboutAction);
        btnHome.setOnMouseClicked(this::btnHomeAction);
    }

    private void btnHomeAction(MouseEvent mouseEvent) {
        Main.screenController.activate("Home");
    }

    private void btnAboutAction(MouseEvent mouseEvent) {
        Main.screenController.activate("About");
    }

    private void btnBuyAction(MouseEvent mouseEvent) {
        Main.screenController.activate("Buy");
    }

    private void btnRemoveAction(ActionEvent actionEvent) {
        try {
            ObservableList<Bus> rows, allRows;
            allRows = table.getItems();
            rows = table.getSelectionModel().getSelectedItems();
            for (Bus b: rows) {
                Main.busData.remove(b);
                buses.remove(b);
                allRows.remove(b);
            }
        } catch (NoSuchElementException ignored) {}
        try {
            Main.sendObj.writeObject("removeBus");
            Main.sendObj.writeObject(Main.busData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void btnAddAction(ActionEvent actionEvent) {
        try {
            Main.sendObj.writeObject("addBus");
            Bus bus = new Bus(Integer.parseInt(tfId.getText()), tfFrom.getText(), tfTo.getText(), tfDate.getValue(), tfTime.getText());
            Main.sendObj.writeObject(bus);
            buses.put(bus, new HashMap<>());
            ObservableList<Bus> allRows = table.getItems();
            allRows.add(bus);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // get add buses
    public ObservableList<Bus> getBus() {
        ObservableList<Bus> allBus = FXCollections.observableArrayList();
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry: Main.busData.entrySet()) {
            allBus.add(entry.getKey());
        }
        return allBus;
    }

    public void btnSettingsAction(MouseEvent mouseEvent) {
//        try {
//        Main.screenController.removeScreen("Buy");
//        Parent buy = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.buyScene)));
//        Main.screenController.addScreen("Buy", 646, 1051, buy);
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
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
