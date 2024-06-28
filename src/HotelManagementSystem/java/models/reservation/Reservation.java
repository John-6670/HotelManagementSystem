package models.reservation;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Reservations")
public class Reservation {
    @DatabaseField(generatedId = true)
    private int ID;
}
