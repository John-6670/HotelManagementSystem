module application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;

    exports application.hotelmanagementsystem;
    opens application.hotelmanagementsystem to javafx.fxml;
}