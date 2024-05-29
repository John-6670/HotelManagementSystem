module application.hotelmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens application.hotelmanagementsystem to javafx.fxml;
    exports application.hotelmanagementsystem;
}