module ticket.counter {
    // Dependency
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires AnimateFX;
    requires java.desktop;

    // Opens
    opens dev.pages.ehsan.main to javafx.fxml;
    opens dev.pages.ehsan.login to javafx.fxml;
    opens dev.pages.ehsan.registration to javafx.fxml;
    opens dev.pages.ehsan.dashboard to javafx.fxml;
    opens dev.pages.ehsan.settings to javafx.fxml;
    opens dev.pages.ehsan.admin to javafx.fxml;
    opens dev.pages.ehsan.buy to javafx.fxml;
    opens dev.pages.ehsan.about to javafx.fxml;
    opens dev.pages.ehsan.user to javafx.base;
    opens dev.pages.ehsan.utils to javafx.base;
    opens lib to javafx.fxml;

    // Exports
    exports dev.pages.ehsan.main to javafx.graphics;
    exports dev.pages.ehsan.login to javafx.graphics;
    exports dev.pages.ehsan.registration to javafx.graphics;
    exports dev.pages.ehsan.dashboard to javafx.graphics;
    exports dev.pages.ehsan.admin to javafx.graphics;
    exports dev.pages.ehsan.settings to javafx.graphics;
    exports dev.pages.ehsan.buy to javafx.graphics;
    exports dev.pages.ehsan.about to javafx.graphics;
    exports dev.pages.ehsan.utils to javafx.graphics;
    exports dev.pages.ehsan.user to javafx.graphics;
    exports dev.pages.ehsan.controllers to javafx.graphics;
    opens dev.pages.ehsan.controllers to javafx.fxml;
}