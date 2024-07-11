package models.user;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable(tableName = "Admin")
public class Admin extends User {

    @DatabaseField
    private String SecurityKey = null;

    @DatabaseField
    private final double salary = 1;

    @DatabaseField(canBeNull = false)
    private final Date registratioDate;

    public Admin(){
        registratioDate = new Date();
        type = RoleType.ADMIN;
        setSecurityKey();
    }

    public Admin(String Name , String NationalID , String PhoneNumber){
        this();
        setName(Name);
        this.nationalId = NationalID;
        setPhoneNumber(PhoneNumber);
    }

    public Admin(String Name , String Username,  String password , String Email , String PhoneNumber , String NationalID){
        super(Name, Username, password, Email, PhoneNumber, NationalID);
        registratioDate = new Date();
        type = RoleType.ADMIN;
        setSecurityKey();
    }

    public void setSecurityKey() {
        String SecurityKey = null;
        //TODO : write method for a random and unique Security Key
        this.SecurityKey = SecurityKey;
    }
}
