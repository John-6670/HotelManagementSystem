package models.user;

import models.bill.Bill;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.exceptions.RoomNotAvailableException;
import models.interfaces.ReservationMaker;
import models.reservation.Reservation;
import models.room.Room;
import models.socket.Request;
import models.socket.Response;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * Guest class represents a guest in the hotel.
 * It extends the User class and implements the ReservationMaker interface.
 *
 * @see User
 * @see ReservationMaker
 * @see Room
 * @see Bill
 * @see Reservation
 *
 * @author John
 */
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

    /**
     * This constructor is used by ORMLite to create an instance of this class.
     */
    public Guest() {
        registrationDate = new Date();
        type = RoleType.GUEST;
    }

    /**
     * This constructor is used to create an instance of this class for just booking a room.
     *
     * @param name the name of the guest
     * @param nationalId the national ID of the guest
     * @param phoneNumber the phone number of the guest
     */
    public Guest(String name, String nationalId, String phoneNumber) {
        this();
        setName(name);
        this.nationalId = nationalId;
        setPhoneNumber(phoneNumber);
    }

    /**
     * This constructor is used to create an instance of this class.
     *
     * @param name the name of the guest
     * @param username the username of the guest
     * @param password the password of the guest
     * @param email the email of the guest
     * @param phoneNumber the phone number of the guest
     * @param nationalID the national ID of the guest
     */
    public Guest(String name, String username, String password, String email, String phoneNumber, String nationalID) {
        super(name, username, password, email, phoneNumber, nationalID);
        registrationDate = new Date();
        type = RoleType.GUEST;
    }

    /**
     * This method is used to set the room of the guest.
     * If the room is available, it will be set to the guest.
     *
     * @param room the room to be set
     */
    public void setRoom(Room room) {
        if (room.getStatus().equals(Room.Status.AVAILABLE)) {
            this.room = room;
            room.setStatus(Room.Status.BOOKED);
        } else
            throw new RoomNotAvailableException("This room isn't available for booking now.");
    }

    /**
     * This method is used to set the bill of the guest.
     *
     * @param bill the bill to be set
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * This method is used to set the reservation of the guest.
     *
     * @param reservation the reservation to be set
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    /**
     * This method is used to get the room of the guest.
     *
     * @return the room of the guest
     */
    public Room getRoom() {
        return room;
    }

    /**
     * This method is used to get the bill of the guest.
     *
     * @return the bill of the guest
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * This method is used to get the reservation of the guest.
     *
     * @return the reservation of the guest
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * This method is used to get the registration date of the guest.
     *
     * @return the registration date of the guest
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * This method is used to make a reservation.
     *
     * @return the reservation made
     */
    @Override
    public Reservation makeReservation(Map data) throws IOException {
        client.sendRequest(new Request(Request.RequestType.BOOK_ROOM, this, data));
        return reservation;
    }
}
