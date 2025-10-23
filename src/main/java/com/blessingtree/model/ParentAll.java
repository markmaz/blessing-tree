package com.blessingtree.model;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="parents")
public class ParentAll {
    public ParentAll(){}

    public ParentAll(Long id, String firstName, String lastName, String primaryPhone, String secondaryPhone,
                  List<ChildAll> children, String btid, Integer mhid) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.children = children;
        this.btid = btid;
        this.MHID = mhid;
    }

    @Id
    @Column(name="parent_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;
    @Column(name="last_name")
    private String lastName;

    @Column(name="phone_1")
    private String primaryPhone;

    @Column(name= "BTID")
    private String btid;

    @Column(name="MHID")
    private Integer MHID;

    @Column(name="phone_2")
    private String secondaryPhone;

    @Column(name="active")
    private Boolean active;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChildAll> children = new ArrayList<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FamilyNote> notes = new ArrayList<>();

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
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

    public List<ChildAll> getChildren() {
        return children;
    }

    public void setChildren(List<ChildAll> children) {
        this.children = children;
    }

    public String getBtid() {
        return btid;
    }

    public void setBtid(String bt_id) {
        this.btid = bt_id;
    }

    public Integer getMHID() {
        return MHID;
    }

    public void setMHID(Integer MHID) {
        this.MHID = MHID;
    }

    public List<FamilyNote> getNotes() {
        return notes;
    }

    public void setNotes(List<FamilyNote> notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParentAll parent)) return false;
        return Objects.equals(id, parent.id) && Objects.equals(firstName, parent.firstName) && Objects.equals(lastName, parent.lastName) && Objects.equals(primaryPhone, parent.primaryPhone) && Objects.equals(btid, parent.btid) && Objects.equals(MHID, parent.MHID) && Objects.equals(secondaryPhone, parent.secondaryPhone) && Objects.equals(children, parent.children) && Objects.equals(notes, parent.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, primaryPhone, btid, MHID, secondaryPhone, children, notes);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", primaryPhone='" + primaryPhone + '\'' +
                ", btid='" + btid + '\'' +
                ", MHID=" + MHID +
                ", secondaryPhone='" + secondaryPhone + '\'' +
                ", children=" + children +
                ", notes=" + notes +
                '}';
    }
}

