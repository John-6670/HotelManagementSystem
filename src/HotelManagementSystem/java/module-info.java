module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires ormlite.jdbc;

    requires com.jfoenix;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
//    requires org.apache.commons.validator;

    exports models.checkInsOuts to ormlite.jdbc;
    opens models.checkInsOuts;


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

    exports models.reservation;
    opens models.reservation to ormlite.jdbc;
    exports models.dataBase;
    opens models.dataBase to ormlite.jdbc;
    exports models.exceptions;
    opens models.exceptions to ormlite.jdbc;
    exports models.socket;
    opens models.socket to ormlite.jdbc;
    exports models.user;
    opens models.user to ormlite.jdbc;
    exports models.bill;
    opens models.bill to ormlite.jdbc;
    exports models.interfaces;
    opens models.interfaces to ormlite.jdbc;
    exports models.room;
    opens models.room to ormlite.jdbc;
    exports models.service;
    opens models.service to ormlite.jdbc;
    exports models;
    opens models to javafx.graphics;

}