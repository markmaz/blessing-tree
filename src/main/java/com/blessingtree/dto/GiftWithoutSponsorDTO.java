package com.blessingtree.dto;

public class GiftWithoutSponsorDTO {
    private Long id;
    private String description;
    private String size;
    private String status;
    private SimpleChildDTO child;
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

    public SimpleChildDTO getChild() {
        return child;
    }

    public void setChild(SimpleChildDTO child) {
        this.child = child;
    }
}
