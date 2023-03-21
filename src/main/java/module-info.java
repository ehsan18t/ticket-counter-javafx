module ticket.counter {
    // Dependency
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires AnimateFX;
    requires java.desktop;

    // Opens
    opens dev.pages.ahsan.main to javafx.fxml;
    opens dev.pages.ahsan.login to javafx.fxml;
    opens dev.pages.ahsan.registration to javafx.fxml;
    opens dev.pages.ahsan.dashboard to javafx.fxml;
    opens dev.pages.ahsan.settings to javafx.fxml;
    opens dev.pages.ahsan.admin to javafx.fxml;
    opens dev.pages.ahsan.buy to javafx.fxml;
    opens dev.pages.ahsan.about to javafx.fxml;
    opens dev.pages.ahsan.user to javafx.base;
    opens dev.pages.ahsan.utils to javafx.base;
    opens lib to javafx.fxml;

    // Exports
    exports dev.pages.ahsan.main to javafx.graphics;
    exports dev.pages.ahsan.login to javafx.graphics;
    exports dev.pages.ahsan.registration to javafx.graphics;
    exports dev.pages.ahsan.dashboard to javafx.graphics;
    exports dev.pages.ahsan.admin to javafx.graphics;
    exports dev.pages.ahsan.settings to javafx.graphics;
    exports dev.pages.ahsan.buy to javafx.graphics;
    exports dev.pages.ahsan.about to javafx.graphics;
    exports dev.pages.ahsan.utils to javafx.graphics;
    exports dev.pages.ahsan.user to javafx.graphics;
}