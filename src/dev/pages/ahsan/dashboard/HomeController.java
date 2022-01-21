package dev.pages.ahsan.dashboard;

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

    @FXML
    private AnchorPane btnAdmin;

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
    private AnchorPane btnBuy;

    @FXML
    private AnchorPane btnAbout;

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

    int total = 0;
    int bought = 0;


    Image image1;
    Image image2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Add Pages
        try {
            Parent settings = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.settingsScene)));
            Main.screenController.addScreen("Settings", 646, 1051, settings);

            Parent admin = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.adminScene)));
            Main.screenController.addScreen("Admin", 646, 1051, admin);

            Parent buy = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.buyScene)));
            Main.screenController.addScreen("Buy", 646, 1051, buy);

            Parent about = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Config.aboutScene)));
            Main.screenController.addScreen("About", 646, 1051, about);
        } catch (IOException e) {
            e.printStackTrace();
        }

        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/res/img/017-menu-6.png")));


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

        System.out.println(" - Logged in as " + Main.user.getName());
        txtUserName.setText(Main.user.getName() + "");

        btnAdmin.setVisible(Main.user.getType().equals("Admin"));
    }

    private void btnAboutAction(MouseEvent mouseEvent) {
        Main.screenController.activate("About");
    }

    // get all tickets of current user
    public ObservableList<Ticket> getTickets() {
        ObservableList<Ticket> allBus = FXCollections.observableArrayList();
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry: Main.busData.entrySet()) {
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
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry: Main.busData.entrySet()) {
            if (entry.getValue().containsKey(Main.user.getEmail())) {
                for (Ticket tk: entry.getValue().get(Main.user.getEmail())) {
                    total++;
                    LocalTime t  = LocalTime.parse(tk.getTime(), timeFormatter);
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
        Main.screenController.activate("Buy");
    }

    private void btnAdminAction(MouseEvent mouseEvent) {
        Main.screenController.activate("Admin");
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
