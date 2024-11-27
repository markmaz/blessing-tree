package com.blessingtree.dto;

import java.util.Objects;

public class TagInfo {
    private String id;
    private String childInfo;

    private String description;

    public TagInfo(){
        this.id = "";
        this.childInfo = "";
        this.description = "";
    }

    public TagInfo(String btid, String name, String age, String gender, String description) {
        this.id = (btid == null) ? "" : btid;
        this.description = (description == null) ? "" : description;

        String sex = "";

        switch (gender){
            case "M":
                sex = "Boy";
                break;
            case "F":
                sex = "Girl";
                break;
            case "O":
                sex = "Unknown";
                break;
            default:
                sex = "";
        }

        name = (name == null) ? "" : name;
        age = (age == null) ? "0" : age;
        int ageTest = 0;

        try{
            ageTest = Integer.parseInt(age);
        }catch (NumberFormatException ex){
        }

        if(ageTest > 18){
            if(sex.equals("Boy")){
                sex = "Man";
            }else if (sex.equals("Girl")){
                sex = "Woman";
            }
        }
        this.childInfo =  "Name: " + name + ", Age: " + age + ", Gender: " + sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildInfo() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagInfo tagInfo)) return false;
        return Objects.equals(id, tagInfo.id) && Objects.equals(childInfo, tagInfo.childInfo) && Objects.equals(description, tagInfo.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, childInfo, description);
    }

    @Override
    public String toString() {
        return "TagInfo{" +
                "id='" + id + '\'' +
                ", childInfo='" + childInfo + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
