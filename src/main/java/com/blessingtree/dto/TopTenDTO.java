package com.blessingtree.dto;

public class TopTenDTO {
    private String giftDescription;
    private Long count;

    public TopTenDTO(String giftDescription, Long count){
        this.giftDescription = giftDescription;
        this.count = count;
    }

    public String getGiftDescription() {
        return giftDescription;
    }

    public void setGiftDescription(String giftDescription) {
        this.giftDescription = giftDescription;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
