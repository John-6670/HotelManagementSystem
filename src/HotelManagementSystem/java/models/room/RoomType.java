package models.room;

/**
 * This enum represents the type of the room.
 * It contains the types of the rooms in the hotel.
 *
 * @author John
 */
public enum RoomType {
    SINGLE(100),
    DOUBLE(150),
    TRIPLE(200),
    QUADRUPLE(250),
    SUIT(200),
    VIP(300);

    private final double price;

    RoomType(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
