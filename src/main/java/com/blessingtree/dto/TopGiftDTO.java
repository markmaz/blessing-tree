package com.blessingtree.dto;

public class TopGiftDTO {
    private Long id;
    private String description;
    private String size;
    private String status;
    private SimpleChildDTO child;
    private SimpleSponsorDTO sponsor;

    public SimpleChildDTO getChild() {
        return child;
    }

    public void setChild(SimpleChildDTO child) {
        this.child = child;
    }

    public SimpleSponsorDTO getSponsor() {
        return sponsor;
    }

    public void setSponsor(SimpleSponsorDTO sponsor) {
        this.sponsor = sponsor;
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
}
