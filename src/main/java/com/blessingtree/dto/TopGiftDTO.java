package com.blessingtree.dto;

public class TopGiftDTO extends GiftDTO{
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
}
