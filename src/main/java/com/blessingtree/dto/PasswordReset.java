package com.blessingtree.dto;

import java.util.Objects;

public class PasswordReset {
    private String token;
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PasswordReset that)) return false;
        return Objects.equals(token, that.token) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, password);
    }

    @Override
    public String toString() {
        return "PasswordReset{" +
                "token='" + token + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
