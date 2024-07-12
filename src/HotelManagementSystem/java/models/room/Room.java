package models.room;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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

    public void setType(RoomType type) {
        this.type = type;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Room(int roomNumber, RoomType type) {
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

    public RoomType getType(String type){
        switch (type) {
            case "Single":
                return RoomType.SINGLE;
            case "Double":
                return RoomType.DOUBLE;
            case "V.I.P":
                return RoomType.VIP;
            case "Triple":
                return RoomType.TRIPLE;
            case "Suit" :
                return RoomType.SUIT;
            case "Quadruple" :
                return RoomType.QUADRUPLE;
        }
        return null;
    }
    public String getStatus(Status status){
        switch (status){
            case Status.AVAILABLE :
                return "Available";
            case Status.BOOKED:
                return "Booked";
            case Status.FULLED:
                return "Fulled";
            case Status.OUT_OF_ORDER:
                return "Out of Order";
            case Status.UNDER_MAINTENANCE:
                return "Under Maintenance";
        }
        return null;
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
