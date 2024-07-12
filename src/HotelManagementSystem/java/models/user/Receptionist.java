package models.user;

import application.hotelmanagementsystem.CommonTasks;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
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


    public void checkIn (Guest guest){

      if (guest.getRoom().getStatus().equals(Room.Status.BOOKED)){
          guest.getRoom().setStatus(Room.Status.FULLED);
          DaoHandler<CheckIn> checkInDaoHandler = new DaoHandler<>(CheckIn.class);
          var checkin = new CheckIn(guest.getRoom(), guest , this);
      }
      else{
          CommonTasks.showError("Reservation Is Not Recognized!");
      }

    }

    public void checkOut (Guest guest){

    }
    ArrayList <CheckIn> checkInList = new ArrayList();
    public void checkInReport (Date startDate , Date endDate){
       for (CheckIn checkIn : checkInList){
           //if ()
       }
    }
    ArrayList <CheckOut> checkOutList = new ArrayList();
    public void checkOutReport (Date startDate , Date endDate){

    }




}
