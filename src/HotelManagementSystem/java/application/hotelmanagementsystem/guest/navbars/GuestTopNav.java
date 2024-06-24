package application.hotelmanagementsystem.guest.navbars;

import application.hotelmanagementsystem.TopNav;
import application.hotelmanagementsystem.guest.dashborad.GuestDashboard;

public class GuestTopNav extends TopNav {
    private GuestDashboard dashboard;

    public void setDashboard(GuestDashboard dashboard) {
        this.dashboard = dashboard;
    }

    @Override
    public void showProfile() {
        dashboard.showProfile();
    }

    @Override
    public void showFinance() {

    }
}
