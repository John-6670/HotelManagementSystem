package models.reservation;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable(tableName = "Reservations")
public class Reservation implements Serializable {
    @DatabaseField(generatedId = true)
    private int ID;
}
