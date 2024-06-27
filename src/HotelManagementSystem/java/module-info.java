module application {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires ormlite.jdbc;
    requires java.sql;
    requires org.apache.commons.validator;

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
    exports application.hotelmanagementsystem.admin.dashboard.room;
    opens application.hotelmanagementsystem.admin.dashboard.room to javafx.fxml;
    exports application.hotelmanagementsystem.admin.dashboard.profile;
    opens application.hotelmanagementsystem.admin.dashboard.profile to javafx.fxml;
    exports application.hotelmanagementsystem.admin.dashboard.staff;
    opens application.hotelmanagementsystem.admin.dashboard.staff to javafx.fxml;
    exports application.hotelmanagementsystem.admin.dashboard.reports;
    opens application.hotelmanagementsystem.admin.dashboard.reports to javafx.fxml;
    exports application.hotelmanagementsystem.admin.dashboard.finanace;
    opens application.hotelmanagementsystem.admin.dashboard.finanace to javafx.fxml;
    exports application.hotelmanagementsystem.guest.dashborad.profile;
    opens application.hotelmanagementsystem.guest.dashborad.profile to javafx.fxml;
    exports application.hotelmanagementsystem.guest.dashborad.reservation;
    opens application.hotelmanagementsystem.guest.dashborad.reservation to javafx.fxml;
    exports application.hotelmanagementsystem.guest.dashborad.room;
    opens application.hotelmanagementsystem.guest.dashborad.room to javafx.fxml;
}