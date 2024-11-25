package com.blessingtree.dto;

import java.util.List;
import java.util.Objects;

public class ChildDTO {
    private Long id;

    private String age;

    private String gender;

    private List<GiftDTO> gifts;

    private String name;

    public ChildDTO(){}

    public ChildDTO(Long id, List<GiftDTO> gifts, String age, String gender, String name){
        this.id = id;
        this.gifts = gifts;
        this.age = age;
        this.gender = gender;
        this.name = name;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChildDTO childDTO)) return false;
        return Objects.equals(id, childDTO.id) && Objects.equals(age, childDTO.age) && Objects.equals(gender, childDTO.gender) && Objects.equals(gifts, childDTO.gifts) && Objects.equals(name, childDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, age, gender, gifts, name);
    }

    @Override
    public String toString() {
        return "ChildDTO{" +
                "id=" + id +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", gifts=" + gifts +
                ", name='" + name + '\'' +
                '}';
    }
}
