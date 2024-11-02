package com.blessingtree.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.List;
import java.util.Objects;

public class ParentDTO {
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Long id;
    private String firstName;
    private String lastName;
    private String primaryPhone;
    private String secondaryPhone;
    private List<ChildDTO> children;
    private String btid;
    private Integer mhid;

   public ParentDTO(Long id, String firstName, String lastName, String primaryPhone, String secondaryPhone,
                    String btid, Integer mhid, List<ChildDTO> children){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.children = children;
        this.btid = btid;
        this.mhid = mhid;
    }

    public ParentDTO(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public List<ChildDTO> getChildren() {
        return children;
    }

    public void setChildren(List<ChildDTO> children) {
        this.children = children;
    }

    public String getBtid() {
        return btid;
    }

    public void setBtid(String bt_id) {
        this.btid = bt_id;
    }

    public Integer getMhid() {
        return mhid;
    }

    public void setMhid(Integer mhid) {
        this.mhid = mhid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentDTO parentDTO)) return false;
        return Objects.equals(id, parentDTO.id) && Objects.equals(firstName, parentDTO.firstName) && Objects.equals(lastName, parentDTO.lastName) && Objects.equals(primaryPhone, parentDTO.primaryPhone) && Objects.equals(secondaryPhone, parentDTO.secondaryPhone) && Objects.equals(children, parentDTO.children) && Objects.equals(btid, parentDTO.btid) && Objects.equals(mhid, parentDTO.mhid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, primaryPhone, secondaryPhone, children, btid, mhid);
    }

    @Override
    public String toString() {
        return "ParentDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", primaryPhone='" + primaryPhone + '\'' +
                ", secondaryPhone='" + secondaryPhone + '\'' +
                ", children=" + children +
                ", btid='" + btid + '\'' +
                ", mhid='" + mhid + '\'' +
                '}';
    }
}
