package models.user;

import application.hotelmanagementsystem.CommonTasks;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.application.Platform;
import models.checkInsOuts.CheckOut;
import models.checkInsOuts.CheckIn;
import models.dataBase.DaoHandler;
import models.room.Room;

import java.util.ArrayList;
import java.util.Date;

@DatabaseTable(tableName = "Receptionist")
public class Receptionist extends User {
    @DatabaseField( columnName = "ConsideredSalary")
    private double salary ;
    @DatabaseField( )
    private Date suspendDate ;
    @DatabaseField(foreign = true, columnName = "registrant", foreignColumnName = "id", foreignAutoRefresh = true)
    private Admin registrant ;
    @DatabaseField( canBeNull = false)
    private Date registrationDate ;

    public Receptionist() {
        this.registrationDate = new Date();
        this.type = RoleType.RECEPTIONIST;
    }

    public Receptionist(double salary, Date suspendDate, Admin registrant) {
        this();
        this.salary = salary;
        this.suspendDate = suspendDate;
        this.registrant = registrant;

    }

    public Receptionist(String name, String username, String password, String email, String phoneNumber, String nationalId, double salary, Date suspendDate, Date registrationDate) {
        super(name, username, password, email, phoneNumber, nationalId);
        this.salary = salary;
        this.suspendDate = suspendDate;
        this.registrant = registrant;
        this.registrationDate = registrationDate;
        this.type = RoleType.RECEPTIONIST;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getSuspendDate() {
        return suspendDate;
    }

    public void setSuspendDate(Date suspendDate) {
        this.suspendDate = suspendDate;
    }

    public Admin getRegistrant() {
        return registrant;
    }


    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Handles the check-in process for a guest.
     * <p>
     * This method updates the room status to FULLED if the room is currently booked.
     * It also creates a new CheckIn record for the guest.
     * If the room is not booked, it displays an error message.
     * </p>
     *
     * @param guest The guest who is checking in.
     */
    public void checkIn (Guest guest){

      if (guest.getRoom().getStatus().equals(Room.Status.BOOKED)){
          guest.getRoom().setStatus(Room.Status.FULLED);
          DaoHandler<CheckIn> checkInDaoHandler = new DaoHandler<>(CheckIn.class);
          var checkin = new CheckIn(guest.getRoom(), guest , this);
      }
      else{
          Platform.runLater(() -> CommonTasks.showError("Reservation Is Not Recognized!"));
      }

    }
    /**
     * Handles the check-out process for a guest.
     * <p>
     * This method updates the room status to AVAILABLE if the room is currently filled.
     * It also creates a new CheckOut record for the guest.
     * If the room is not filled, it displays an error message.
     * </p>
     *
     * @param guest The guest who is checking out.
     */
    public void checkOut (Guest guest){
        if (guest.getRoom().getStatus().equals(Room.Status.FULLED)){
            guest.getRoom().setStatus(Room.Status.AVAILABLE);
            DaoHandler<CheckOut> checkOutDaoHandler = new DaoHandler<>(CheckOut.class);
            var checkout = new CheckOut(guest.getRoom(), guest , this);

        }
        else{
            CommonTasks.showError("Wrong Room !");
        }


    }



}
