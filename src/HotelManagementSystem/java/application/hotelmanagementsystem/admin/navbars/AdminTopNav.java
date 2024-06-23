package application.hotelmanagementsystem.admin.navbars;

import application.hotelmanagementsystem.CloseButton;
import application.hotelmanagementsystem.TopNav;
import application.hotelmanagementsystem.admin.dashboard.AdminDashboard;
import javafx.fxml.FXML;

public class AdminTopNav extends TopNav {
    private AdminDashboard dashboard;

    public void setDashboard(AdminDashboard dashboard) {
        this.dashboard = dashboard;
    }


    @Override
    public void showProfile() {

    }

    @Override
    public void showFinance() {

    }
}
