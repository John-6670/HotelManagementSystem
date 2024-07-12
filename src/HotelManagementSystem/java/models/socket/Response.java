package models.socket;

import java.io.Serializable;

/**
 * This class represents a response that is sent from the server to the client.
 * It contains the type of the response and the data that is sent to the client.
 *
 * @see Request
 * @see Client
 * @see Server
 * @see ResponseType
 *
 * @author John
 */
public class Response implements Serializable {
    /**
     * This enum represents the type of the response.
     */
    public enum ResponseType {
        FAIL, SUCCESS;
    }

    private ResponseType type;
    private Object data;

    /**
     * This constructor is used to create an instance of the Response class.
     *
     * @param responseType the type of the response
     * @param data the data that is sent to the client
     */
    public Response(ResponseType responseType, Object data) {
        this.type = responseType;
        this.data = data;
    }

    /**
     * This method is used to get the type of the response.
     *
     * @return the type of the response
     */
    public ResponseType getResponseType() {
        return type;
    }

    /**
     * This method is used to get the data that is sent to the client.
     *
     * @return the data that is sent to the client
     */
    public Object getData() {
        return data;
    }

    /**
     * This method is used to set the type of the response.
     *
     * @param responseType the type of the response
     */
    public void setResponseType(ResponseType responseType) {
        this.type = responseType;
    }

    /**
     * This method is used to set the data that is sent to the client.
     *
     * @param data the data that is sent to the client
     */
    public void setData(Object data) {
        this.data = data;
    }
}
