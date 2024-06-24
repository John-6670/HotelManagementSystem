package application.hotelmanagementsystem.admin.dashboard.staff;

import application.hotelmanagementsystem.CommonTasks;

public class StaffSample {
    public void loadSuspend() {
        CommonTasks.loadPopUp("admin/dashboard/staff/suspend-view.fxml");
    }

    public void fire() {
        CommonTasks.showConfirmation("This receptionist will be fired!!");
    }
}
