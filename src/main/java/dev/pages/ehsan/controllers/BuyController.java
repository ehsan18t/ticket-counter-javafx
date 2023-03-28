package dev.pages.ehsan.controllers;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideOutLeft;
import dev.pages.ehsan.classes.Bus;
import dev.pages.ehsan.classes.KeyValuePair;
import dev.pages.ehsan.classes.Ticket;
import dev.pages.ehsan.main.Config;
import dev.pages.ehsan.main.Main;
import dev.pages.ehsan.utils.Utils;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.*;

public class BuyController implements Initializable {

    HashMap<Bus, HashMap<String, ArrayList<Ticket>>> buses;
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
    private ImageView btnMenu;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private AnchorPane mainPaneHome;
    @FXML
    private AnchorPane btnHome;
    @FXML
    private AnchorPane btnSettings;
    @FXML
    private AnchorPane btnAbout;
    @FXML
    private AnchorPane btnAdmin;
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
    @FXML
    private Text txtBuy;
    private boolean logout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        logout = false;
        if (!Main.user.getType().equalsIgnoreCase("admin"))
            serverListener();
        seats = new ArrayList<>();
        initBtn();
        image1 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/menu-expand.png")));
        image2 = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/img/017-menu-6.png")));

        // set
        menuPane.setVisible(false);
        btnAdmin.setVisible(false);
        txtTitle.setText(Config.title + " " + Config.version);
        txtUserName.setText(Main.user.getName() + "");
        txtEmail.setText(Main.user.getEmail() + "");
        txtPhone.setText(Main.user.getPhone() + "");
        btnBuy.setDisable(true);

        // Action Event
        btnClose.setOnMouseClicked(this::setBtnCloseAction);
        btnMin.setOnMouseClicked(this::setBtnMinAction);
        btnLogout.setOnMouseClicked(this::btnLogoutAction);
        btnBuy.setOnAction(this::btnBuyAction);
        choiceBus.setOnAction(this::choiceBusAction);


        // Manu Action
        btnMenu.setOnMouseClicked(this::btnMenuAction);
        btnAbout.setOnMouseClicked(this::btnAboutAction);
        btnSettings.setOnMouseClicked(this::btnSettingsAction);
        btnHome.setOnMouseClicked(this::btnHomeAction);
        btnAdmin.setOnMouseClicked(this::btnAdminAction);

        if (Main.user.getType().equals("Admin")) {
            btnAdmin.setVisible(true);
            btnHome.setVisible(false);
            txtBuy.setText("History");
            btnBuy.setVisible(false);
        }

        addValues();
    }

    private void btnAdminAction(MouseEvent mouseEvent) {
        logout();
        Main.sceneMan.open("admin", Config.adminScene);
    }

    private void btnHomeAction(MouseEvent mouseEvent) {
        logout();
        Main.sceneMan.open("home", Config.homeScene);
    }

    private void btnAboutAction(MouseEvent mouseEvent) {
        logout();
        Main.sceneMan.open("about", Config.aboutScene);
    }

    private void resetSeatState() {
        for (ToggleButton tb : seats) {
            tb.setDisable(false);
            tb.setSelected(false);
        }
    }

    private void resetItems() {
        try {
            choiceBus.getItems().clear();
            addValues();
        } catch (Exception ignored) {
        }
    }

    private void loadSeatData() {
        try {
//        if (choiceBus.getValue() != null || choiceBus.getValue().getKey() != null) {
            System.out.println(Main.busData.containsKey(choiceBus.getValue().getKey()));
            HashMap<String, ArrayList<Ticket>> data = Main.busData.get(choiceBus.getSelectionModel().getSelectedItem().getKey());
            for (Map.Entry<String, ArrayList<Ticket>> entry : data.entrySet()) {
                for (Ticket t : entry.getValue()) {
                    // Converting seat number to button index
                    String s = t.getSeat();
                    int x = s.charAt(0) - 'A';
                    int index = (x * 4) + (s.charAt(1) - '0');

                    // if bought by current classes
                    if (entry.getKey().equals(Main.user.getEmail())) {
                        seats.get(index - 1).setSelected(true);
                        seats.get(index - 1).setDisable(true);
                    } else {
                        seats.get(index - 1).setDisable(true);
                    }
//                }
                }
            }
        } catch (Exception ignored) {
        }
    }


    private void loadSeatData(int i) {
        try {
            System.out.println(" - Loading data");
            choiceBus.getSelectionModel().select(i);
            System.out.println(Main.busData.containsKey(choiceBus.getValue().getKey()));
            HashMap<String, ArrayList<Ticket>> data = Main.busData.get(choiceBus.getSelectionModel().getSelectedItem().getKey());
            for (Map.Entry<String, ArrayList<Ticket>> entry : data.entrySet()) {
                for (Ticket t : entry.getValue()) {
                    // Converting seat number to button index
                    String s = t.getSeat();
                    int x = s.charAt(0) - 'A';
                    int index = (x * 4) + (s.charAt(1) - '0');

                    // if bought by current classes
                    if (entry.getKey().equals(Main.user.getEmail())) {
                        seats.get(index - 1).setSelected(true);
                        seats.get(index - 1).setDisable(true);
                    } else {
                        seats.get(index - 1).setDisable(true);
                    }
                }
            }
        } catch (NullPointerException ignored) {
        }
    }

    private void choiceBusAction(ActionEvent actionEvent) {
        btnBuy.setDisable(false);
        resetSeatState();
        loadSeatData();
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
                        int i = choiceBus.getSelectionModel().getSelectedIndex();
                        resetItems();
                        loadSeatData(i);
                    }
                } catch (Exception ignored) {
                }
            }
        }).start();
    }

    private void btnBuyAction(ActionEvent actionEvent) {
        Bus bus = choiceBus.getSelectionModel().getSelectedItem().getKey();
        String user = Main.user.getEmail();

        // If classes data not exist create
        if (!Main.busData.get(bus).containsKey(user))
            Main.busData.get(bus).put(user, new ArrayList<>());
        // else just retrieve the previous data
        ArrayList<Ticket> list = Main.busData.get(bus).get(user);

        for (ToggleButton b : seats) {
            if (b.isSelected() && !b.isDisabled()) {
                list.add(new Ticket(bus.getFrom(), bus.getTo(), bus.getDate(), bus.getTime(), b.getText(), bus.getId()));
                b.setSelected(true);
                b.setDisable(true);
            }
        }
        try {
            Main.sendObj.writeObject("buy");
            Main.sendObj.writeObject(Main.busData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void addValues() {
        DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("hh:mm a").toFormatter(Locale.ENGLISH);
        LocalTime ct = LocalTime.now();
        LocalDate cd = LocalDate.now();
        for (Map.Entry<Bus, HashMap<String, ArrayList<Ticket>>> entry : Main.busData.entrySet()) {
            LocalTime t = LocalTime.parse(entry.getKey().getTime(), timeFormatter);
            if (cd.isAfter(entry.getKey().getDate()))
                continue;
            if (cd.isEqual(entry.getKey().getDate()))
                if (ct.isAfter(t))
                    continue;

            String value = entry.getKey().getId() + ". " + entry.getKey().getFrom() + " - " + entry.getKey().getTo() + " [" + entry.getKey().getDate() + " - " + entry.getKey().getTime() + "]";
            choiceBus.getItems().add(new KeyValuePair(entry.getKey(), value));
        }
        choiceBus.getItems().sort(Comparator.comparing(KeyValuePair::toString));
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

    private void logout() {
        logout = true;
        try {
            Main.sendObj.writeObject("logout");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            Main.sendObj.writeObject("logout");
        } catch (IOException ignored) {
        }
        if (Utils.removeFile(Config.userTempData) && Utils.removeFile(Config.savedUserData)) {
            logout = true;
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
