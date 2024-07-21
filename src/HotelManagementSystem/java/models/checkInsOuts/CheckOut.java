package models.checkInsOuts;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import models.user.Receptionist;
import models.user.Guest ;
import models.room.Room ;

import java.io.Serializable;
import java.util.Date ;

@DatabaseTable(tableName = "CheckOut")
public class CheckOut implements Serializable {

    @DatabaseField( canBeNull = false)
    private Date date ;
    @DatabaseField(foreign = true, columnName = "room_number", foreignColumnName = "room_number", foreignAutoRefresh = true)
    private Room room ;
    @DatabaseField(foreign = true, columnName = "guest", foreignColumnName = "ID", foreignAutoRefresh = true)
    private Guest guest ;
    @DatabaseField(foreign = true, columnName = "receptionist", foreignColumnName = "ID", foreignAutoRefresh = true)
    private Receptionist receptionist ;

    public CheckOut() {
        date = new Date () ;
    }

    public CheckOut(Room room, Guest guest, Receptionist receptionist) {
        this () ;
        this.room = room;
        this.guest = guest;
        this.receptionist = receptionist;
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Receptionist getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(Receptionist receptionist) {
        this.receptionist = receptionist;
    }
}


