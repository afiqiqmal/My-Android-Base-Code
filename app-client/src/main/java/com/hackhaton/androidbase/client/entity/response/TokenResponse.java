package com.hackhaton.androidbase.client.entity.response;

/**
 * @author : hafiq on 23/01/2017.
 */

public class TokenResponse {

    private boolean status;
    private String token;
    private String message;



    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}