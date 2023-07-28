package com.example.demo.models;

public class ResponseObject {
    private String status_code;
    private String message;
    private Object data;

    public ResponseObject() {}

    public ResponseObject(String status_code, String message, Object data) {
        this.status_code = status_code;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status_code;
    }

    public void setStatus(String status) {
        this.status_code = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}