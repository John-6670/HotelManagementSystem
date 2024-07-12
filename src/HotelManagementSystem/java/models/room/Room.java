package models.room;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Room class is a model class that represents a room in the hotel.
 * It contains the room number, type, status, price, and capacity of the room.
 * The room number is unique and is used to identify the room.
 * The type of the room is an enum that represents the type of the room.
 * The status of the room is an enum that represents the status of the room.
 * The price of the room is a double that represents the price of the room.
 * The capacity of the room is an integer that represents the capacity of the room.
 *
 * @see RoomType
 * @see Status
 *
 * @author John
 */
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

    /**
     * This constructor is used by ORMLite to create an instance of this class.
     */
    public Room() {}

    /**
     * This constructor is used to create an instance of this class.
     *
     * @param roomNumber the room number
     * @param type the type of the room
     * @throws SQLException if the room type is not valid
     */
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

    /**
     * This method is used to set the status of the room.
     *
     * @param status the status of the room
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * This method is used to get the room number.
     *
     * @return the room number
     */
    public int getRoomNumber() {
        return roomNumber;
    }

    /**
     * This method is used to get the type of the room.
     *
     * @return the type of the room
     */
    public RoomType getType() {
        return type;
    }

    /**
     * This method is used to get the status of the room.
     *
     * @return the status of the room
     */
    public Status getStatus() {
        return status;
    }

    /**
     * This method is used to get the price of the room.
     *
     * @return the price of the room
     */
    public double getPrice() {
        return price;
    }

    /**
     * This method is used to get the capacity of the room.
     *
     * @return the capacity of the room
     */
    public int getCapacity() {
        return capacity;
    }

    public enum Status {
        AVAILABLE, BOOKED, FULLED, UNDER_MAINTENANCE, OUT_OF_ORDER
    }
}
