package dev.pages.ehsan.controllers;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ehsan.classes.Bus;
import dev.pages.ehsan.classes.Ticket;
import dev.pages.ehsan.main.Config;
import dev.pages.ehsan.main.Main;
import dev.pages.ehsan.utils.Utils;
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
    Image image1;
    Image image2;
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
    @FXML
    private Text txtBuy;
    private boolean logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logout = false;
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/017-menu-6.png")));

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

        if (Main.user.getType().equals("Admin")) {
            btnHome.setVisible(false);
            txtBuy.setText("History");
        }

        serverListener();
    }

    private void logout() {
        logout = true;
        try {
            Main.sendObj.writeObject("logout");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void serverListener() {
        new Thread(() -> {
            while (true) {
                try {
                    String response = (String) Main.receiveObj.readObject();
                    System.out.println(" [*] Received cmd from server " + response);

                    if (logout) {
                        Thread.interrupted();
                        break;
                    }

                    Main.busData = (HashMap<Bus, HashMap<String, ArrayList<Ticket>>>) Main.receiveObj.readObject();
                    if (response.contains("refresh")) {
                        table.setItems(getBus());
                    }
                } catch (Exception ignored) {
                }
            }
        });
    }


    private void btnHomeAction(MouseEvent mouseEvent) {
        logout();
        Main.sceneMan.open("home", Config.homeScene);
    }

    private void btnAboutAction(MouseEvent mouseEvent) {
        logout();
        Main.sceneMan.open("about", Config.aboutScene);
    }

    private void btnBuyAction(MouseEvent mouseEvent) {
        logout();
        Main.sceneMan.open("buy", Config.buyScene);
    }

    private void btnRemoveAction(ActionEvent actionEvent) {
        ObservableList<Bus> original = FXCollections.observableArrayList();
        HashMap<Bus, HashMap<String, ArrayList<Ticket>>> busData = new HashMap<>();
        try {
            ObservableList<Bus> rows, allRows;
            allRows = table.getItems();
            rows = table.getSelectionModel().getSelectedItems();
            for (Bus b : rows) {
                original.add(b);
                Main.busData.remove(b);
                busData.put(b, Main.busData.get(b));
                buses.remove(b);
                allRows.remove(b);
            }
        } catch (NoSuchElementException ignored) {
        }
        try {
            Main.sendObj.writeObject("removeBus");
            Main.sendObj.writeObject(Main.busData);
        } catch (Exception e) {
            // if exception occurs original item restored to avoid data loss on client side
            for (Bus b : original)
                Main.busData.put(b, busData.get(b));

            table.setItems(original);
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
        if (Main.busData == null) Main.busData = new HashMap<>();
        ObservableList<Bus> allBus = FXCollections.observableArrayList();
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry : Main.busData.entrySet()) {
            allBus.add(entry.getKey());
        }
        return allBus;
    }

    public void btnSettingsAction(MouseEvent mouseEvent) {
        logout();
        Main.sceneMan.open("settings", Config.settingsScene);
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
        logout();
        if (Utils.removeFile(Config.userTempData) && Utils.removeFile(Config.savedUserData)) {
            System.out.println(" - Logout Successful!");
        }
        Main.sceneMan.reload("login");
        Main.sceneMan.activate("login");
    }

    private void setBtnCloseAction(MouseEvent event) {
        logout();
        Utils.exit();
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
