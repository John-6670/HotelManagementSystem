package models.socket;

import models.user.User;

import java.io.Serializable;

public class Request implements Serializable {
    public enum RequestType {
        LOGIN, SIGNUP, SEARCH_ROOM, BOOK_ROOM, EXTEND_RESERVATION,
        CHECK_RESERVATION, REQUEST_SERVICE, PAY_BILL, CHECK_IN, CHECK_OUT,
        UPDATE_ROOM, ADD_ROOM, REMOVE_ROOM, UPDATE_RECEPTIONIST,
        ADD_RECEPTIONIST, REMOVE_RECEPTIONIST, VIEW_REPORTS
    }

    private RequestType type;
    private User user;
    private Object data;

    public Request(RequestType requestType, User user, Object data) {
        this.type = requestType;
        this.user = user;
        this.data = data;
    }

    public RequestType getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public Object getData() {
        return data;
    }

    public void setRequestType(RequestType requestType) {
        this.type = requestType;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
