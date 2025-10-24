package com.blessingtree.dto;

import java.util.Objects;

public class GiftDTO {
    private Long id;
    private String description;

    private SimpleSponsorDTO sponsor;
    private String size;
    private String status;
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SimpleSponsorDTO getSponsor() {
        return sponsor;
    }

    public void setSponsor(SimpleSponsorDTO sponsor) {
        this.sponsor = sponsor;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftDTO giftDTO)) return false;
        return Objects.equals(id, giftDTO.id) && Objects.equals(description, giftDTO.description) &&  Objects.equals(size, giftDTO.size) && Objects.equals(status, giftDTO.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, size, status);
    }

    @Override
    public String toString() {
        return "GiftDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
