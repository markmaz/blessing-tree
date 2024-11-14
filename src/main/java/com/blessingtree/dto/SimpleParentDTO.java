package com.blessingtree.dto;

public class SimpleParentDTO {
    private Long id;
    private String lastName;
    private String firstName;
    private String BTID;
    private String primaryPhone;
    private String emailAddress;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getBTID() {
        return BTID;
    }

    public void setBTID(String BTID) {
        this.BTID = BTID;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhone;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhone = primaryPhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }
}
