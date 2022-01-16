module ticket.counter {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    opens dev.pages.ahsan.main to javafx.fxml;
    opens dev.pages.ahsan.login to javafx.fxml;
    opens dev.pages.ahsan.registration to javafx.fxml;
    exports dev.pages.ahsan.main to javafx.graphics;
    exports dev.pages.ahsan.login to javafx.graphics;
    exports dev.pages.ahsan.registration to javafx.graphics;
    exports dev.pages.ahsan.utils to javafx.graphics;
}