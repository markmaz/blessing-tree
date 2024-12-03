package com.blessingtree.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="sponsors")
public class Sponsor extends AuditRecord {
    public Sponsor(){}

    @Id
    @Column(name="sponsor_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "sponsor", fetch = FetchType.LAZY)
    private List<Gift> gifts;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

//    @OneToMany(mappedBy="sponsor", cascade=CascadeType.ALL, orphanRemoval=true)
//    private List<SponsorYear> sponsorYear = new ArrayList<>();

    @Column(name="phone")
    private String phone;

    @Column(name="email_address")
    private String email;

    @Column(name="best_time_to_call")
    private String bestTimeToCall;

    @Column(name="has_sponsored_previously")
    private Boolean hasSponsoredPreviously;

    @Column(name="how_did_you_hear")
    private String howDidYouHearAboutUs;

    @Column(name="want_to_volunteer")
    private Boolean wantToVolunteer;

    @Column(name="child_age_preference")
    private String childAgePreference;

    @Column(name="gender_preference")
    private String genderPreference;

    @Column(name="number_of_children_sponsored")
    private Integer numberOfChildrenSponsored = 0;

    @OneToMany(mappedBy = "sponsor", fetch = FetchType.LAZY)
    private List<CallLog> logEntries;

    @Column(name="giftStatus")
    private String giftStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
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

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public List<CallLog> getLogEntries() {
        return logEntries;
    }

    public void setLogEntries(List<CallLog> logEntries) {
        this.logEntries = logEntries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sponsor sponsor)) return false;
        return Objects.equals(id, sponsor.id) && Objects.equals(address, sponsor.address) && Objects.equals(gifts, sponsor.gifts) && Objects.equals(lastName, sponsor.lastName) && Objects.equals(firstName, sponsor.firstName) && Objects.equals(phone, sponsor.phone) && Objects.equals(email, sponsor.email) && Objects.equals(bestTimeToCall, sponsor.bestTimeToCall) && Objects.equals(hasSponsoredPreviously, sponsor.hasSponsoredPreviously) && Objects.equals(howDidYouHearAboutUs, sponsor.howDidYouHearAboutUs) && Objects.equals(wantToVolunteer, sponsor.wantToVolunteer) && Objects.equals(childAgePreference, sponsor.childAgePreference) && Objects.equals(genderPreference, sponsor.genderPreference) && Objects.equals(numberOfChildrenSponsored, sponsor.numberOfChildrenSponsored) && Objects.equals(logEntries, sponsor.logEntries) && Objects.equals(giftStatus, sponsor.giftStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, gifts, lastName, firstName, phone, email, bestTimeToCall, hasSponsoredPreviously, howDidYouHearAboutUs, wantToVolunteer, childAgePreference, genderPreference, numberOfChildrenSponsored, logEntries, giftStatus);
    }

    @Override
    public String toString() {
        return "Sponsor{" +
                "id=" + id +
                ", address=" + address +
                ", gifts=" + gifts +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
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
