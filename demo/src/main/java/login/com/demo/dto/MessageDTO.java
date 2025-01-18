package login.com.demo.dto;

public class MessageDTO {
    private String message;

    private boolean result;

    public String getStatus() {
        return status;
    }

    public MessageDTO(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public MessageDTO(String message, String status, boolean result) {
        this.message = message;
        this.status = status;
        this.result = result;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String status;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }
}
