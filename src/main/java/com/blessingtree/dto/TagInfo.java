package com.blessingtree.dto;

import java.util.Objects;

public class TagInfo {
    private String id;
    private String childInfo;
    private String name;
    private String age;
    private String gender = "";


    private String description;

    public TagInfo(){
        this.id = "";
        this.childInfo = "";
        this.description = "";
    }

    public TagInfo(String btid, String name, String age, String gender, String description) {
        this.id = (btid == null) ? "" : btid;
        this.description = (description == null) ? "" : description;
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildInfo() {
        String sex = switch (gender) {
            case "M" -> "Boy";
            case "F" -> "Girl";
            case "O" -> "Unknown";
            default -> "";
        };

        name = (name == null) ? "" : name;
        age = (age == null) ? "0" : age;
        int ageTest = 0;

        try{
            ageTest = Integer.parseInt(age);
        }catch (NumberFormatException ignored){
        }

        if(ageTest > 18){
            if(sex.equals("Boy")){
                sex = "Man";
            }else if (sex.equals("Girl")){
                sex = "Woman";
            }
        }
        this.childInfo =  "Name: " + name + ", Age: " + age + ", Gender: " + sex;
        return childInfo;
    }

    public void setChildInfo(String childInfo) {
        this.childInfo = childInfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagInfo tagInfo)) return false;
        return Objects.equals(id, tagInfo.id) && Objects.equals(childInfo, tagInfo.childInfo) && Objects.equals(name, tagInfo.name) && Objects.equals(age, tagInfo.age) && Objects.equals(gender, tagInfo.gender) && Objects.equals(description, tagInfo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, childInfo, name, age, gender, description);
    }

    @Override
    public String toString() {
        return "TagInfo{" +
                "id='" + id + '\'' +
                ", childInfo='" + childInfo + '\'' +
                ", name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
