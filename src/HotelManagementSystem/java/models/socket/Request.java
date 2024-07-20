package models.socket;

import models.user.User;

import java.io.Serializable;

public class Request implements Serializable {
    public enum RequestType {
        LOGIN, SIGNUP, UPDATE_INFO, DELETE_ACCOUNT,

        BOOK_ROOM, CHANGE_RESERVATION, REQUEST_SERVICE, PAY_BILL,

        SEARCH_ROOM,  CHECK_IN, CHECK_OUT, BOOK_FOR_GUEST,
        CHECK_IN_CHECK_OUT_REPORT, BILL_REPORT,

        UPDATE_ROOM, ADD_ROOM, REMOVE_ROOM, ADD_RECEPTIONIST, SEARCH_IF_ROOM_EXISTS ,  CHECK_If_PASS_IS_VALID,
        REMOVE_RECEPTIONIST, VIEW_REPORTS, STAFF_REPORTS, ADD_REPORT, GET_ADMIN_KEY , GET_ALL_ROOMS
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
