package models.room;

import application.hotelmanagementsystem.receptionist.navbars.ReceptionistSideNav;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.reservation.Reservation;

import java.io.Serializable;
import java.sql.SQLException;

@DatabaseTable(tableName = "Room")
public class Room implements Serializable {
    @DatabaseField(canBeNull = false, unique = true, id = true, columnName = "room_number")
    private int roomNumber;

    @DatabaseField(canBeNull = false)
    private RoomType type;

    @DatabaseField(canBeNull = false)
    private Status status;

    @DatabaseField(canBeNull = false)
    private double price;

    @DatabaseField(canBeNull = false)
    private int capacity;

    public Room() {}

    public Room(int roomNumber, RoomType type) throws SQLException {
        this.roomNumber = roomNumber;
        this.type = type;
        price = type.getPrice();
        status = Status.AVAILABLE;

        capacity = switch (type) {
            case SINGLE -> 1;
            case DOUBLE, VIP -> 2;
            case TRIPLE, SUIT -> 3;
            case QUADRUPLE -> 4;
        };
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getType() {
        return type;
    }

    public Status getStatus() {
        return status;
    }

    public double getPrice() {
        return price;
    }

    public int getCapacity() {
        return capacity;
    }

    public enum Status {
        AVAILABLE, BOOKED, FULLED, UNDER_MAINTENANCE, OUT_OF_ORDER
    }
}
