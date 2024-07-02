package models.user;

import com.j256.ormlite.field.DatabaseField;
import models.exceptions.*;
//import org.apache.commons.validator.routines.EmailValidator;
import models.socket.Client;

import java.io.Serializable;

// TODO: resolve email validate issue
public abstract class User implements Serializable {
    public enum RoleType {
        ADMIN, GUEST, RECEPTIONIST
    }

    @DatabaseField(canBeNull = false)
    protected String name;

    @DatabaseField(generatedId = true)
    protected int ID;

    @DatabaseField(unique = true)
    protected String username;

    @DatabaseField
    protected String password;

    @DatabaseField(unique = true)
    protected String email;

    @DatabaseField(unique = true)
    protected String phoneNumber;

    @DatabaseField(canBeNull = false)
    protected RoleType type;

    @DatabaseField(canBeNull = false, unique = true)
    protected String nationalId;

    protected transient Client client;


    protected User() {}

    protected User(String name, String username, String password, String email, String phoneNumber, String nationalId) {
        setPassword(password);
        setName(name);
        setPhoneNumber(phoneNumber);
        this.nationalId = nationalId;
        setEmail(email);
        setPassword(password);
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        if (isValidPassword(password))
            this.password = password;
        else
            throw new InvalidPasswordException("For better security, your" +
                    " password needs to be at least 8 characters long and" +
                    " include a mix of uppercase and lowercase letters, numbers," +
                    " and special characters..");
    }

    public void setEmail(String email) {
//        if (isValidEmail(email))
            this.email = email;
//        else
//            throw new InvalidEmailException("The email address you entered" +
//                    " seems to be invalid. Please double-check and try again.");
    }

    public void setPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
        else
            throw new InvalidPhoneNumberException("The phone number you entered" +
                    " seems to be invalid. Please double-check and try again.");
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getID() {
        return ID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public RoleType getType() {
        return type;
    }

    public String getNationalId() {
        return nationalId;
    }

    public Client getClient() {
        return client;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 8)
            return false;

        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumber = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if (Character.isDigit(c)) {
                hasNumber = true;
            } else if (!Character.isWhitespace(c)) { // Exclude whitespace
                hasSpecialChar = true;
            }
        }

        return hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
    }

//    private boolean isValidEmail(String email) {
//        if (email == null)
//            return true;
//
//        EmailValidator validator = EmailValidator.getInstance();
//        return validator.isValid(email);
//    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null)
            return true;

        return phoneNumber.chars().allMatch(Character::isDigit) &&
                phoneNumber.startsWith("09") && phoneNumber.length() == 11;
    }
}
