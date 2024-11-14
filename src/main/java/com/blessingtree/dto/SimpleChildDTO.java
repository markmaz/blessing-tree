package com.blessingtree.dto;

public class SimpleChildDTO {
    private Long id;
    private String gender;
    private String age;
    private SimpleParentDTO parent;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public SimpleParentDTO getParent() {
        return parent;
    }

    public void setParent(SimpleParentDTO parent) {
        this.parent = parent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
