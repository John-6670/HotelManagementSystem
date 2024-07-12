package models.user;

public class Admin extends User {
    public Admin() {
    }

    public Admin(String name, String username, String password, String email, String phoneNumber, String nationalId) {
        super(name, username, password, email, phoneNumber, nationalId);
    }
}
