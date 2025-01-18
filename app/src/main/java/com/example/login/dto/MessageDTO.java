package com.example.login.dto;

public class MessageDTO {
    private String message;

    public String getStatus() {
        return status;
    }

    public MessageDTO(String message, String status) {
        this.message = message;
        this.status = status;
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
}
