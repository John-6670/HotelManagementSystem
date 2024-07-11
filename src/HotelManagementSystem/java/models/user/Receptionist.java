package models.user;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.checkInsOuts.CheckOut;
import models.checkInsOuts.CheckIn;
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
    public Receptionist(){
        registrationDate = new Date();
    }

    public Receptionist(double salary, Date suspendDate, Admin registrant) {
        this();
        this.salary = salary;
        this.suspendDate = suspendDate;
        this.registrant = registrant;

    }

    public Receptionist(String name, String username, String password, String email, String phoneNumber, String nationalId, double salary, Date suspendDate, Admin registrant, Date registrationDate) {
        super(name, username, password, email, phoneNumber, nationalId);
        this.salary = salary;
        this.suspendDate = suspendDate;
        this.registrant = registrant;
        this.registrationDate = registrationDate;
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
      }

    }

    public void checkOut (Guest guest){

    }

    public void checkInReport (Date startDate , Date endDate){

    }
    ArrayList <CheckIn> checkInList = new ArrayList();
    public void checkOutReport (Date startDate , Date endDate){

    }
    ArrayList <CheckOut> checkOutList = new ArrayList();



}
