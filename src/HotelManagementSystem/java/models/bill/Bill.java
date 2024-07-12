package models.bill;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;

@DatabaseTable(tableName = "Bill")
public class Bill implements Serializable {
    @DatabaseField(generatedId = true)
    private int ID;

    @DatabaseField(canBeNull = false)
    private double roomCharge;

    @DatabaseField(defaultValue = "0")
    private double additionalServices = 0;

    public Bill() {}

    public Bill(double roomCharge) throws SQLException {
        this.roomCharge = roomCharge;
    }

    public void increaseAdditionalServices(double price) {
        additionalServices += price;
    }

    public int getID() {
        return ID;
    }

    public double getRoomCharge() {
        return roomCharge;
    }

    public double getAdditionalServices() {
        return additionalServices;
    }

    public double calculateBill() {
        return roomCharge + additionalServices;
    }

    public void pay() {
        roomCharge = 0;
        additionalServices = 0;
    }
}
