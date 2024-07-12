package models.bill;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * This class represents a bill in the system.
 * It contains the attributes and methods that are related to a bill.
 *
 * @author John
 */
@DatabaseTable(tableName = "Bill")
public class Bill implements Serializable {
    @DatabaseField(generatedId = true)
    private int ID;

    @DatabaseField(canBeNull = false)
    private double roomCharge;

    @DatabaseField(defaultValue = "0")
    private double additionalServices = 0;

    /**
     * This constructor is used by ORMLite to create an instance of this class.
     */
    public Bill() {}

    /**
     * This constructor is used to create an instance of this class.
     *
     * @param roomCharge the charge of the room
     */
    public Bill(double roomCharge) throws SQLException {
        this.roomCharge = roomCharge;
    }

    /**
     * This method is used to increase the additional services charge.
     *
     * @param price the price of the additional services
     */
    public void increaseAdditionalServices(double price) {
        additionalServices += price;
    }

    /**
     * This method is used to get the ID of the bill.
     *
     * @return the ID of the bill
     */
    public int getID() {
        return ID;
    }

    /**
     * This method is used to get the charge of the room.
     *
     * @return the charge of the room
     */
    public double getRoomCharge() {
        return roomCharge;
    }

    /**
     * This method is used to get the charge of the additional services.
     *
     * @return the charge of the additional services
     */
    public double getAdditionalServices() {
        return additionalServices;
    }

    /**
     * This method is used to calculate the total bill.
     *
     * @return the total bill
     */
    public double calculateBill() {
        return roomCharge + additionalServices;
    }

    /**
     * This method is used to pay the bill.
     */
    public void pay() {
        roomCharge = 0;
        additionalServices = 0;
    }
}
