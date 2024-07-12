package models.socket;

import models.user.User;

import java.io.Serializable;

/**
 * This class represents a request that is sent from the client to the server.
 * It contains the type of the request, the user that is making the request, and the data that is sent with the request.
 *
 * @see Response
 * @see Client
 * @see Server
 * @see RequestType
 *
 * @author John
 */
public class Request implements Serializable {
    /**
     * This enum represents the type of the request.
     */
    public enum RequestType {
        LOGIN, SIGNUP, UPDATE_INFO, DELETE_ACCOUNT,

        BOOK_ROOM, CHANGE_RESERVATION, REQUEST_SERVICE, PAY_BILL,

        SEARCH_ROOM,  CHECK_IN, CHECK_OUT, BOOK_FOR_GUEST,
        CHECK_IN_CHECK_OUT_REPORT, BILL_REPORT,

        UPDATE_ROOM, ADD_ROOM, REMOVE_ROOM, ADD_RECEPTIONIST,
        REMOVE_RECEPTIONIST, VIEW_REPORTS, STAFF_REPORTS, ADD_REPORT, GET_ADMIN_KEY
    }

    private RequestType type;
    private User user;
    private Object data;

    /**
     * This constructor is used to create an instance of the Request class.
     *
     * @param requestType the type of the request
     * @param user the user that is making the request
     * @param data the data that is sent with the request
     */
    public Request(RequestType requestType, User user, Object data) {
        this.type = requestType;
        this.user = user;
        this.data = data;
    }

    /**
     * This method is used to get the type of the request.
     *
     * @return the type of the request
     */
    public RequestType getType() {
        return type;
    }

    /**
     * This method is used to get the user that is making the request.
     *
     * @return the user that is making the request
     */
    public User getUser() {
        return user;
    }

    /**
     * This method is used to get the data that is sent with the request.
     *
     * @return the data that is sent with the request
     */
    public Object getData() {
        return data;
    }

    /**
     * This method is used to set the user of the request.
     *
     * @param user the user of the request
     */
    public void setUser(User user) {
        this.user = user;
    }
}
