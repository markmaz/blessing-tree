package com.blessingtree.dto;

import java.util.List;
import java.util.Objects;

public class SeniorDTO {
    private Long id;

    private String firstName;

    private String lastName;

    private String nursingHome;

    private List<GiftDTO> gifts;

    private SponsorDTO sponsor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNursingHome() {
        return nursingHome;
    }

    public void setNursingHome(String nursingHome) {
        this.nursingHome = nursingHome;
    }

    public List<GiftDTO> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftDTO> gifts) {
        this.gifts = gifts;
    }

    public SponsorDTO getSponsor() {
        return sponsor;
    }

    public void setSponsor(SponsorDTO sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SeniorDTO seniorDTO)) return false;
        return Objects.equals(id, seniorDTO.id) && Objects.equals(firstName, seniorDTO.firstName) && Objects.equals(lastName, seniorDTO.lastName) && Objects.equals(nursingHome, seniorDTO.nursingHome) && Objects.equals(gifts, seniorDTO.gifts) && Objects.equals(sponsor, seniorDTO.sponsor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nursingHome, gifts, sponsor);
    }

    @Override
    public String toString() {
        return "SeniorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nursingHome='" + nursingHome + '\'' +
                ", gifts=" + gifts +
                ", sponsor=" + sponsor +
                '}';
    }
}
