package application.hotelmanagementsystem.receptionist.navbars;

import application.hotelmanagementsystem.TopNav;
import application.hotelmanagementsystem.receptionist.dashboard.ReceptionistDashboard;

public class ReceptionistTopNav extends TopNav {
    private ReceptionistDashboard dashboard;

    public void setDashboard(ReceptionistDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void showProfile() {
        dashboard.showProfile();
    }

    @Override
    public void showFinance() {}
}
