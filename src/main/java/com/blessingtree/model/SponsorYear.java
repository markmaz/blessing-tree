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
    @JoinColumn(name="sponsor_id")
    private Sponsor sponsor;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SponsorYear that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(genderPreference, that.genderPreference) && Objects.equals(year, that.year) && Objects.equals(numberOfChildrenSponsored, that.numberOfChildrenSponsored) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, genderPreference, year, numberOfChildrenSponsored);
    }

    @Override
    public String toString() {
        return "SponsorYear{" +
                "id=" + id +
                ", genderPreference='" + genderPreference + '\'' +
                ", year=" + year +
                '}';
    }
}
