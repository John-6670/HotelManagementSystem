package models.reservation;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.room.Room;
import models.user.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Represents a reservation in the system.
 * Each reservation is associated with a room and a guest.
 *
 * @see Room
 * @see Admin
 *
 * @author Arefe
 */
@DatabaseTable(tableName = "Reservations")
public class Reservation {
    @DatabaseField(generatedId = true)
    private int ID;

    @DatabaseField
    private Date startDate;

    @DatabaseField
    private Date endDate;

    @DatabaseField(foreign = true, columnName = "room_number", foreignColumnName = "room_number", foreignAutoRefresh = true)
    private Room room;

    @DatabaseField(foreign = true, columnName = "guest_id", foreignColumnName = "ID", foreignAutoRefresh = true)
    private Guest guest;

    /**
     * Default constructor for ORMLite.
     */
    public Reservation() {}

    /**
     * Constructs a new Reservation with the specified details.
     *
     * @param startDate the start date of the reservation
     * @param endDate   the end date of the reservation
     * @param room      the room associated with the reservation
     * @param guest     the guest associated with the reservation
     */
    public Reservation(Date startDate, Date endDate, Room room, Guest guest) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.room = room;
        this.guest = guest;
    }

    /**
     * Gets the reservation ID.
     *
     * @return the reservation ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Gets the start date of the reservation.
     *
     * @return the start date of the reservation
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the reservation.
     *
     * @param startDate the start date to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date of the reservation.
     *
     * @return the end date of the reservation
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the reservation.
     *
     * @param endDate the end date to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the room associated with the reservation.
     *
     * @return the room associated with the reservation
     */
    public Room getRoom() {
        return room;
    }

    /**
     * Sets the room associated with the reservation.
     *
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Gets the guest associated with the reservation.
     *
     * @return the guest associated with the reservation
     */
    public Guest getGuest() {
        return guest;
    }

    /**
     * Sets the guest associated with the reservation.
     *
     * @param guest the guest to set
     */
    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    /**
     * Extends the reservation by the specified number of days.
     *
     * @param days the number of days to extend the reservation
     */
    public void extendReservation(int days) {
        long newEndDateMillis = this.endDate.getTime() + TimeUnit.DAYS.toMillis(days);
        this.endDate = new Date(newEndDateMillis);
    }
}
