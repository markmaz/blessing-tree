package com.blessingtree.dto;

import java.util.Objects;

public class CallLogDTO {
    private Long id;

    private String callDate;

    private String callDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallDescription() {
        return callDescription;
    }

    public void setCallDescription(String callDescription) {
        this.callDescription = callDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallLogDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(callDate, that.callDate) && Objects.equals(callDescription, that.callDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, callDate, callDescription);
    }

    @Override
    public String toString() {
        return "CallLogDTO{" +
                "id=" + id +
                ", callDate='" + callDate + '\'' +
                ", callDescription='" + callDescription + '\'' +
                '}';
    }
}
