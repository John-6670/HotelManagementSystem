package models.user;

import models.bill.Bill;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.exceptions.RoomNotAvailableException;
import models.interfaces.ReservationMaker;
import models.reservation.Reservation;
import models.room.Room;

import java.sql.SQLException;
import java.util.Date;

@DatabaseTable(tableName = "Guest")
public class Guest extends User implements ReservationMaker {
    @DatabaseField(foreign = true, columnName = "room_number", foreignColumnName = "room_number", foreignAutoRefresh = true)
    private Room room = null;

    @DatabaseField(foreign = true, columnName = "bill_id", foreignColumnName = "ID", foreignAutoRefresh = true)
    private Bill bill = null;

    @DatabaseField(foreign = true, columnName = "reservation_id", foreignColumnName = "ID", foreignAutoRefresh = true)
    private Reservation reservation;

    @DatabaseField(canBeNull = false)
    private final Date registrationDate;

    public Guest() {
        registrationDate = new Date();
        type = RoleType.GUEST;
    }

    public Guest(String name, String nationalId, String phoneNumber) {
        this();
        setName(name);
        this.nationalId = nationalId;
        setPhoneNumber(phoneNumber);
    }

    public Guest(String name, String username, String password, String email, String phoneNumber, String nationalID) throws SQLException {
        super(name, username, password, email, phoneNumber, nationalID);
        registrationDate = new Date();
        type = RoleType.GUEST;
    }

    public void setRoom(Room room) {
        if (room.getStatus().equals(Room.Status.AVAILABLE)) {
            this.room = room;
            room.setStatus(Room.Status.BOOKED);
        } else
            throw new RoomNotAvailableException("This room isn't available for booking now.");
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Room getRoom() {
        return room;
    }

    public Bill getBill() {
        return bill;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    // TODO: complete method
    @Override
    public Reservation makeReservation() {
        return null;
    }

    // TODO: complete method
    public void serviceRequest() {
        return;
    }
}
