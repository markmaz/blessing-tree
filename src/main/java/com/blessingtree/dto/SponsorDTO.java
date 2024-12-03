package com.blessingtree.dto;

import com.blessingtree.model.SponsorYear;
import jakarta.persistence.Column;

import java.util.List;
import java.util.Objects;

public class SponsorDTO {
    private Long id;
    private AddressDTO address;
    private List<GiftWithoutSponsorDTO> gifts;
    private String lastName;
    private String firstName;
    private List<SponsorYearDTO> sponsorYear;
    private String phone;
    private String email;
    private String bestTimeToCall;
    private Boolean hasSponsoredPreviously;
    private String howDidYouHearAboutUs;
    private Boolean wantToVolunteer;
    private String childAgePreference;
    private String genderPreference;
    private Integer numberOfChildrenSponsored;
    private List<CallLogDTO> logEntries;
    private String giftStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<GiftWithoutSponsorDTO> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftWithoutSponsorDTO> gifts) {
        this.gifts = gifts;
    }

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

    public List<SponsorYearDTO> getSponsorYear() {
        return sponsorYear;
    }

    public void setSponsorYear(List<SponsorYearDTO> sponsorYear) {
        this.sponsorYear = sponsorYear;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBestTimeToCall() {
        return bestTimeToCall;
    }

    public void setBestTimeToCall(String bestTimeToCall) {
        this.bestTimeToCall = bestTimeToCall;
    }

    public Boolean getHasSponsoredPreviously() {
        return hasSponsoredPreviously;
    }

    public void setHasSponsoredPreviously(Boolean hasSponsoredPreviously) {
        this.hasSponsoredPreviously = hasSponsoredPreviously;
    }

    public String getHowDidYouHearAboutUs() {
        return howDidYouHearAboutUs;
    }

    public void setHowDidYouHearAboutUs(String howDidYouHearAboutUs) {
        this.howDidYouHearAboutUs = howDidYouHearAboutUs;
    }

    public Boolean getWantToVolunteer() {
        return wantToVolunteer;
    }

    public void setWantToVolunteer(Boolean wantToVolunteer) {
        this.wantToVolunteer = wantToVolunteer;
    }

    public String getChildAgePreference() {
        return childAgePreference;
    }

    public void setChildAgePreference(String childAgePreference) {
        this.childAgePreference = childAgePreference;
    }

    public String getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(String genderPreference) {
        this.genderPreference = genderPreference;
    }

    public Integer getNumberOfChildrenSponsored() {
        return numberOfChildrenSponsored;
    }

    public void setNumberOfChildrenSponsored(Integer numberOfChildrenSponsored) {
        this.numberOfChildrenSponsored = numberOfChildrenSponsored;
    }

    public List<CallLogDTO> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<CallLogDTO> logEntries) {
        this.logEntries = logEntries;
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SponsorDTO that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(address, that.address) && Objects.equals(gifts, that.gifts) && Objects.equals(lastName, that.lastName) && Objects.equals(firstName, that.firstName) && Objects.equals(sponsorYear, that.sponsorYear) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(bestTimeToCall, that.bestTimeToCall) && Objects.equals(hasSponsoredPreviously, that.hasSponsoredPreviously) && Objects.equals(howDidYouHearAboutUs, that.howDidYouHearAboutUs) && Objects.equals(wantToVolunteer, that.wantToVolunteer) && Objects.equals(childAgePreference, that.childAgePreference) && Objects.equals(genderPreference, that.genderPreference) && Objects.equals(numberOfChildrenSponsored, that.numberOfChildrenSponsored) && Objects.equals(logEntries, that.logEntries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, gifts, lastName, firstName, sponsorYear, phone, email, bestTimeToCall, hasSponsoredPreviously, howDidYouHearAboutUs, wantToVolunteer, childAgePreference, genderPreference, numberOfChildrenSponsored, logEntries, giftStatus);
    }

    @Override
    public String toString() {
        return "SponsorDTO{" +
                "id=" + id +
                ", address=" + address +
                ", gifts=" + gifts +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", sponsorYear=" + sponsorYear +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", bestTimeToCall='" + bestTimeToCall + '\'' +
                ", hasSponsoredPreviously=" + hasSponsoredPreviously +
                ", howDidYouHearAboutUs='" + howDidYouHearAboutUs + '\'' +
                ", wantToVolunteer=" + wantToVolunteer +
                ", childAgePreference='" + childAgePreference + '\'' +
                ", genderPreference='" + genderPreference + '\'' +
                ", numberOfChildrenSponsored=" + numberOfChildrenSponsored +
                ", logEntries=" + logEntries +
                ", giftStatus='" + giftStatus + '\'' +
                '}';
    }
}
