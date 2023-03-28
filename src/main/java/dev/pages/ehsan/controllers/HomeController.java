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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class HomeController implements Initializable {

    int total = 0;
    int bought = 0;
    Image image1;
    Image image2;
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
    private AnchorPane btnAdmin;
    @FXML
    private TableView<Ticket> table;
    @FXML
    private TableColumn<Ticket, String> timeCol;
    @FXML
    private TableColumn<Ticket, String> toCol;
    @FXML
    private TableColumn<Ticket, LocalDate> dateCol;
    @FXML
    private TableColumn<Ticket, String> fromCol;
    @FXML
    private TableColumn<Ticket, String> seatCol;
    @FXML
    private TableColumn<Ticket, Integer> busIdCol;
    @FXML
    private Text txtEmail;
    @FXML
    private Text txtPhone;
    @FXML
    private Text txtBuyCount;
    @FXML
    private Text txtTotalCount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnAdmin.setVisible(false);
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/017-menu-6.png")));

        // get data from server
        try {
            Main.sendObj.writeObject("getBusData");
            Main.busData = (HashMap<Bus, HashMap<String, ArrayList<Ticket>>>) Main.receiveObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Table
        busIdCol.setCellValueFactory(new PropertyValueFactory<>("bus"));
        seatCol.setCellValueFactory(new PropertyValueFactory<>("seat"));
        fromCol.setCellValueFactory(new PropertyValueFactory<>("from"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        table.setItems(getTickets());

        // set
        menuPane.setVisible(false);
        txtTitle.setText(Config.title + " " + Config.version);
        txtUserName.setText(Main.user.getName() + "");
        txtEmail.setText(Main.user.getEmail() + "");
        txtPhone.setText(Main.user.getPhone() + "");
        count();
        txtBuyCount.setText(bought + "");
        txtTotalCount.setText(total + "");

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);

        // Manu Action
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnBuy.setOnMouseClicked(this::btnBuyAction);
        btnSettings.setOnMouseClicked(this::btnSettingsAction);
        btnAbout.setOnMouseClicked(this::btnAboutAction);
        btnAdmin.setOnMouseClicked(this::btnAdminAction);

        System.out.println(" - Logged in as " + Main.user.getName() + " [" + Main.user.getType() + "]");
        txtUserName.setText(Main.user.getName() + "");
    }

    private void btnAboutAction(MouseEvent mouseEvent) {
        Main.sceneMan.open("about", Config.aboutScene);
    }

    // get all tickets of current classes
    public ObservableList<Ticket> getTickets() {
        if (Main.busData == null) Main.busData = new HashMap<>();
        ObservableList<Ticket> allBus = FXCollections.observableArrayList();
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry : Main.busData.entrySet()) {
            if (entry.getValue().containsKey(Main.user.getEmail()))
                allBus.addAll(entry.getValue().get(Main.user.getEmail()));
        }
        // sort by date
        allBus.sort(Comparator.comparing(Ticket::getDate));
        return allBus;
    }


    private void count() {
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mm a").toFormatter(Locale.ENGLISH);
        LocalTime ct = LocalTime.now();
        LocalDate cd = LocalDate.now();
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry : Main.busData.entrySet()) {
            if (entry.getValue().containsKey(Main.user.getEmail())) {
                for (Ticket tk : entry.getValue().get(Main.user.getEmail())) {
                    total++;
                    LocalTime t = LocalTime.parse(tk.getTime(), timeFormatter);
                    if (cd.isAfter(tk.getDate()))
                        continue;
                    if (cd.isEqual(tk.getDate()))
                        if (ct.isAfter(t))
                            continue;
                    bought++;
                }
            }

        }
    }


    private void btnBuyAction(MouseEvent mouseEvent) {
        Main.sceneMan.open("buy", Config.buyScene);
    }

    private void btnAdminAction(MouseEvent mouseEvent) {
        Main.sceneMan.open("admin", Config.adminScene);
    }

    public void btnSettingsAction(MouseEvent mouseEvent) {
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
        if (Utils.removeFile(Config.userTempData) && Utils.removeFile(Config.savedUserData)) {
            System.out.println(" - Logout Successful!");
        }
        Main.sceneMan.reload("login");
        Main.sceneMan.activate("login");
    }

    private void setBtnCloseAction(MouseEvent event) {
        Utils.exit();
    }

    private void setBtnMinAction(MouseEvent event) {
        Stage stage = (Stage) ((ImageView) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
