module application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    exports application.hotelmanagementsystem;
    opens application.hotelmanagementsystem to javafx.fxml;
    exports application.hotelmanagementsystem.admin;
    opens application.hotelmanagementsystem.admin to javafx.fxml;
    exports application.hotelmanagementsystem.guest;
    opens application.hotelmanagementsystem.guest to javafx.fxml;
}