package utils;

public class ResponseDeprecated<T> {
    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
    public T getData() {
        return data;
    }

}


