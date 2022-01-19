module ticket.counter {
    // Dependency
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires animateFX;

    // Opens
    opens dev.pages.ahsan.main to javafx.fxml;
    opens dev.pages.ahsan.login to javafx.fxml;
    opens dev.pages.ahsan.registration to javafx.fxml;
    opens dev.pages.ahsan.dashboard to javafx.fxml;
    opens dev.pages.ahsan.settings to javafx.fxml;
    opens dev.pages.ahsan.admin to javafx.fxml;
    opens lib to javafx.fxml;

    // Exports
    exports dev.pages.ahsan.main to javafx.graphics;
    exports dev.pages.ahsan.login to javafx.graphics;
    exports dev.pages.ahsan.registration to javafx.graphics;
    exports dev.pages.ahsan.dashboard to javafx.graphics;
    exports dev.pages.ahsan.admin to javafx.graphics;
    exports dev.pages.ahsan.settings to javafx.graphics;
    exports dev.pages.ahsan.utils to javafx.graphics;
    exports dev.pages.ahsan.user to javafx.graphics;
}