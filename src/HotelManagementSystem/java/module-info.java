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
    exports application.hotelmanagementsystem.guest.dashborad;
    opens application.hotelmanagementsystem.guest.dashborad to javafx.fxml;
    exports application.hotelmanagementsystem.guest.login;
    opens application.hotelmanagementsystem.guest.login to javafx.fxml;
    exports application.hotelmanagementsystem.admin.dashboard;
    opens application.hotelmanagementsystem.admin.dashboard to javafx.fxml;
    exports application.hotelmanagementsystem.admin.login;
    opens application.hotelmanagementsystem.admin.login to javafx.fxml;
    exports application.hotelmanagementsystem.guest.navbars;
    opens application.hotelmanagementsystem.guest.navbars to javafx.fxml;
    exports application.hotelmanagementsystem.admin.navbars;
    opens application.hotelmanagementsystem.admin.navbars to javafx.fxml;
    exports application.hotelmanagementsystem.receptionist.dashboard;
    opens application.hotelmanagementsystem.receptionist.dashboard to javafx.fxml;
    exports application.hotelmanagementsystem.receptionist.login;
    opens application.hotelmanagementsystem.receptionist.login to javafx.fxml;
    exports application.hotelmanagementsystem.receptionist.navbars;
    opens application.hotelmanagementsystem.receptionist.navbars to javafx.fxml;
}