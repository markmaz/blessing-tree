package com.blessingtree.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sponsor_year")
public class SponsorYear extends AuditRecord{
    public SponsorYear(){}

    @Id
    @Column(name="sponsor_year_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gender_preference")
    private String genderPreference;

    @Column(name="year")
    private Long year;

    @Column(name="number_of_children_sponsored")
    private Long numberOfChildrenSponsored;

    @ManyToOne
    @JoinColumn(name="sponsor_id", nullable = false)
    private Sponsor sponsor;

    @Column(name= "child_age_preference")
    private String childAgePreference;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGenderPreference() {
        return genderPreference;
    }

    public void setGenderPreference(String genderPreference) {
        this.genderPreference = genderPreference;
    }

    public Long getYear() {
        return year;
    }

    public void setYear(Long year) {
        this.year = year;
    }

    public Long getNumberOfChildrenSponsored() {
        return numberOfChildrenSponsored;
    }

    public void setNumberOfChildrenSponsored(Long numberOfChildrenSponsored) {
        this.numberOfChildrenSponsored = numberOfChildrenSponsored;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String getChildAgePreference() {
        return childAgePreference;
    }

    public void setChildAgePreference(String childAgePreference) {
        this.childAgePreference = childAgePreference;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SponsorYear that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(genderPreference, that.genderPreference) && Objects.equals(year, that.year) && Objects.equals(numberOfChildrenSponsored, that.numberOfChildrenSponsored) && Objects.equals(sponsor, that.sponsor) && Objects.equals(childAgePreference, that.childAgePreference);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genderPreference, year, numberOfChildrenSponsored, sponsor, childAgePreference);
    }

    @Override
    public String toString() {
        return "SponsorYear{" +
                "id=" + id +
                ", genderPreference='" + genderPreference + '\'' +
                ", year=" + year +
                ", numberOfChildrenSponsored=" + numberOfChildrenSponsored +
                ", sponsor=" + sponsor +
                ", childAgePreference='" + childAgePreference + '\'' +
                '}';
    }
}
