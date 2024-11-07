package com.blessingtree.dto;

import java.util.Objects;

public class SponsorYearDTO {
    private String genderPreference;
    private Long year;
    private Long numberOfChildrenSponsored;

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
        if (!(o instanceof SponsorYearDTO that)) return false;
        return Objects.equals(genderPreference, that.genderPreference) && Objects.equals(year, that.year) && Objects.equals(numberOfChildrenSponsored, that.numberOfChildrenSponsored);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genderPreference, year, numberOfChildrenSponsored);
    }

    @Override
    public String toString() {
        return "SponsorYearDTO{" +
                "genderPreference='" + genderPreference + '\'' +
                ", year=" + year +
                ", numberOfChildrenSponsored=" + numberOfChildrenSponsored +
                '}';
    }
}
