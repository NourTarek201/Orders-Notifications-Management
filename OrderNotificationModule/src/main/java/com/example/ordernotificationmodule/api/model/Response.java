package com.example.ordernotificationmodule.api.model;

public class Response {

    private boolean status;
    private Object result;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean b) {
        this.status = b;
    }

    // public String getMessage() {
    // 	return message;
    // }

    // public void setMessage(String message) {
    // 	this.message = message;
    // }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
