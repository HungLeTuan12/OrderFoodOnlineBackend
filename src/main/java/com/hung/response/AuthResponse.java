package com.hung.response;

import com.hung.model.USER_ROLE;

public class AuthResponse {
    private String jwt;
    private String message;
    private USER_ROLE role;
    // Constructor

    public AuthResponse() {
    }

    public AuthResponse(String jwt, String message, USER_ROLE role) {
        this.jwt = jwt;
        this.message = message;
        this.role = role;
    }
    // Getter and setter

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public USER_ROLE getRole() {
        return role;
    }

    public void setRole(USER_ROLE role) {
        this.role = role;
    }
}
