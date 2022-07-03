package utils;

public class Response<T> {
    public String code;
    public String message;
    public T data;

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


