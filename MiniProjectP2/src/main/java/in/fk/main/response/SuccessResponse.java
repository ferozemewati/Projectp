package in.fk.main.response;

public class SuccessResponse<T> {
    private String message;
    private int status;
    private T data;

    public SuccessResponse(String message, int status, T data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() { return message; }
    public int getStatus() { return status; }
    public T getData() { return data; }
}