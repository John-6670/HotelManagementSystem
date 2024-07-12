package models.user;

import com.j256.ormlite.field.DatabaseField;
import models.exceptions.*;
import models.socket.Client;

import java.io.Serializable;

// TODO: resolve email validate issue

/**
 * This class represents a user in the system.
 * It is an abstract class that is extended by the Admin, Guest, and Receptionist classes.
 * It contains the common attributes and methods that are shared between these classes.
 *
 * @see Admin
 * @see Guest
 * @see Receptionist
 *
 * @author John
 */
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

    /**
     * This constructor is used by ORMLite to create an instance of this class.
     */
    protected User() {}

    /**
     * This constructor is used to create an instance of this class.
     *
     * @param name the name of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param email the email of the user
     * @param phoneNumber the phone number of the user
     * @param nationalId the national ID of the user
     */
    protected User(String name, String username, String password, String email, String phoneNumber, String nationalId) {
        setPassword(password);
        setName(name);
        setPhoneNumber(phoneNumber);
        this.nationalId = nationalId;
        setEmail(email);
        setPassword(password);
        this.username = username;
    }

    /**
     * This method is used to set the name of the user.
     *
     * @param name the name of the user
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method is used to set the password of the user.
     * It checks if the password is valid before setting it.
     *
     * @param password the password of the user
     */
    public void setPassword(String password) {
        if (isValidPassword(password))
            this.password = password;
        else
            throw new InvalidPasswordException("For better security, your" +
                    " password needs to be at least 8 characters long and" +
                    " include a mix of uppercase and lowercase letters, numbers," +
                    " and special characters..");
    }

    /**
     * This method is used to set the email of the user.
     *
     * @param email the email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * This method is used to set the phone number of the user.
     * It checks if the phone number is valid before setting it.
     *
     * @param phoneNumber the phone number of the user
     */
    public void setPhoneNumber(String phoneNumber) {
        if (isValidPhoneNumber(phoneNumber))
            this.phoneNumber = phoneNumber;
        else
            throw new InvalidPhoneNumberException("The phone number you entered" +
                    " seems to be invalid. Please double-check and try again.");
    }

    /**
     * This method is used to set a client for the user.
     *
     * @param client the client user uses to connect to the server
     */
    public void setClient(Client client) {
        this.client = client;
    }

    /**
     * This method is used to get the name of the user.
     *
     * @return the name of the user
     */
    public String getName() {
        return name;
    }

    /**
     * This method is used to get the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method is used to get the password of the user.
     *
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method is used to get the email of the user.
     *
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method is used to get the ID of the user.
     *
     * @return the ID of the user
     */
    public int getID() {
        return ID;
    }

    /**
     * This method is used to get the phone number of the user.
     *
     * @return the phone number of the user
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * This method is used to get the type of the user.
     *
     * @return the type of the user
     */
    public RoleType getType() {
        return type;
    }

    /**
     * This method is used to get the national ID of the user.
     *
     * @return the national ID of the user
     */
    public String getNationalId() {
        return nationalId;
    }

    /**
     * This method is used to get the client of the user.
     *
     * @return the client of the user
     */
    public Client getClient() {
        return client;
    }

    /**
     * This method is used to check if the password is valid.<br><br>
     * A valid password should have following properties:<br>
     * - At least 8 characters long<br>
     * - Include a mix of uppercase and lowercase letters<br>
     * - Include numbers<br>
     * - Include special characters
     *
     * @param password the password to be checked
     * @return true if the password is valid, false otherwise
     */
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

    /**
     * This method is used to check if the phone number is valid.<br><br>
     * A valid phone number should have following properties:<br>
     * - Start with "09"<br>
     * - Have 11 digits
     *
     * @param phoneNumber the phone number to be checked
     * @return true if the phone number is valid, false otherwise
     */
    private boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null)
            return true;

        return phoneNumber.chars().allMatch(Character::isDigit) &&
                phoneNumber.startsWith("09") && phoneNumber.length() == 11;
    }
}
