module ticket.counter {
    // Dependencies
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires AnimateFX;
    requires java.desktop;

    // exports
    opens dev.pages.ehsan.main to javafx.fxml;
    exports dev.pages.ehsan.main;
    opens dev.pages.ehsan.controllers to javafx.fxml;
    exports dev.pages.ehsan.controllers;
    opens dev.pages.ehsan.classes to javafx.base;
    exports dev.pages.ehsan.classes;
    opens dev.pages.ehsan.utils to javafx.base;
    exports dev.pages.ehsan.utils;
}