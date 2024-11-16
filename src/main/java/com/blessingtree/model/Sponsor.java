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

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany
    @JoinColumn(name="sponsor_id")
    private List<Gift> gifts;

    @Column(name="last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @OneToMany(mappedBy="sponsor", cascade=CascadeType.ALL, orphanRemoval=true)
    private List<SponsorYear> sponsorYear = new ArrayList<>();

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

    public List<SponsorYear> getSponsorYear() {
        return sponsorYear;
    }

    public void setSponsorYear(List<SponsorYear> sponsorYear) {
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

    public void addSponsorYear(SponsorYear sponsorYear) {
        this.sponsorYear.add(sponsorYear);
        sponsorYear.setSponsor(this);
    }

    public void removeSponsorYear(SponsorYear sponsorYear) {
        this.sponsorYear.remove(sponsorYear);
        sponsorYear.setSponsor(null);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sponsor sponsor)) return false;
        return Objects.equals(id, sponsor.id) && Objects.equals(address, sponsor.address) && Objects.equals(gifts, sponsor.gifts) && Objects.equals(lastName, sponsor.lastName) && Objects.equals(firstName, sponsor.firstName) && Objects.equals(sponsorYear, sponsor.sponsorYear) && Objects.equals(phone, sponsor.phone) && Objects.equals(email, sponsor.email) && Objects.equals(bestTimeToCall, sponsor.bestTimeToCall) && Objects.equals(hasSponsoredPreviously, sponsor.hasSponsoredPreviously) && Objects.equals(howDidYouHearAboutUs, sponsor.howDidYouHearAboutUs) && Objects.equals(wantToVolunteer, sponsor.wantToVolunteer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, gifts, lastName, firstName, sponsorYear, phone, email, bestTimeToCall, hasSponsoredPreviously, howDidYouHearAboutUs, wantToVolunteer);
    }

    @Override
    public String toString() {
        return "Sponsor{" +
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
                '}';
    }
}
