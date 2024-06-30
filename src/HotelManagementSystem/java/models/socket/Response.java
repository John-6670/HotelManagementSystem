package models.socket;

import java.io.Serializable;

public class Response implements Serializable {
    public enum ResponseType {
        FAIL, SUCCESS;
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
