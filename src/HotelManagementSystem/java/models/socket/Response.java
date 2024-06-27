package models.socket;

import java.io.Serializable;

public class Response implements Serializable {
    public enum ResponseType {
        LOGIN_SUCCESS, LOGIN_FAIL, SIGNUP_SUCCESS, SIGNUP_FAIL,
        SEARCH_ROOM_SUCCESS, SEARCH_ROOM_FAIL, BOOK_ROOM_SUCCESS, BOOK_ROOM_FAIL,
        EXTEND_RESERVATION_SUCCESS, EXTEND_RESERVATION_FAIL, CHECK_RESERVATION_SUCCESS, CHECK_RESERVATION_FAIL,
        REQUEST_SERVICE_SUCCESS, REQUEST_SERVICE_FAIL, PAY_BILL_SUCCESS, PAY_BILL_FAIL,
        CHECK_IN_SUCCESS, CHECK_IN_FAIL, CHECK_OUT_SUCCESS, CHECK_OUT_FAIL,
        UPDATE_ROOM_SUCCESS, UPDATE_ROOM_FAIL, ADD_ROOM_SUCCESS, ADD_ROOM_FAIL,
        REMOVE_ROOM_SUCCESS, REMOVE_ROOM_FAIL, UPDATE_RECEPTIONIST_SUCCESS, UPDATE_RECEPTIONIST_FAIL,
        ADD_RECEPTIONIST_SUCCESS, ADD_RECEPTIONIST_FAIL, REMOVE_RECEPTIONIST_SUCCESS, REMOVE_RECEPTIONIST_FAIL,
        VIEW_REPORTS_SUCCESS, VIEW_REPORTS_FAIL
    }

    private ResponseType type;
    private Object data;

    public Response(ResponseType responseType, Object data) {
        this.type = responseType;
        this.data = data;
    }

    public ResponseType getResponseType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public void setResponseType(ResponseType responseType) {
        this.type = responseType;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
