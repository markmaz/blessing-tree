package com.blessingtree.dto;

import java.util.Objects;

public class UserDTO {
    private Long userID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private boolean isAdmin;
    private String status = "active";

    public UserDTO(String username, String password, String firstName, String lastName, String emailAddress, boolean isAdmin, String status, Long userID) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.isAdmin = isAdmin;
        this.status = status;
        this.userID = userID;
    }

    public UserDTO(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO userDTO)) return false;
        return isAdmin == userDTO.isAdmin && Objects.equals(userID, userDTO.userID) && Objects.equals(username, userDTO.username) && Objects.equals(password, userDTO.password) && Objects.equals(firstName, userDTO.firstName) && Objects.equals(lastName, userDTO.lastName) && Objects.equals(emailAddress, userDTO.emailAddress) && Objects.equals(status, userDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, username, password, firstName, lastName, emailAddress, isAdmin, status);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userID=" + userID +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", isAdmin=" + isAdmin +
                ", status='" + status + '\'' +
                '}';
    }
}
