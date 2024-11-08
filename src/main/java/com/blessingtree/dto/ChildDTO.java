package com.blessingtree.dto;

import java.util.List;
import java.util.Objects;

public class ChildDTO {
    private Long id;

    private Long age;

    private String gender;

    private List<GiftDTO> gifts;

    private SponsorDTO sponsor;

    public ChildDTO(){}

    public ChildDTO(Long id, List<GiftDTO> gifts, Long age, String gender){
        this.id = id;
        this.gifts = gifts;
        this.age = age;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<GiftDTO> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftDTO> gifts) {
        this.gifts = gifts;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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
        if (!(o instanceof ChildDTO childDTO)) return false;
        return Objects.equals(id, childDTO.id) && Objects.equals(age, childDTO.age) && Objects.equals(gender, childDTO.gender) && Objects.equals(gifts, childDTO.gifts) && Objects.equals(sponsor, childDTO.sponsor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, gender, gifts, sponsor);
    }

    @Override
    public String toString() {
        return "ChildDTO{" +
                "id=" + id +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", gifts=" + gifts +
                ", sponsor=" + sponsor +
                '}';
    }
}
