package com.blessingtree.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="adults")
public class Senior extends AuditRecord {
    public Senior(){}

    @Id
    @Column(name="adult_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="nursing_home")
    private String nursingHome;

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

    public String getNursingHome() {
        return nursingHome;
    }

    public void setNursingHome(String nursingHome) {
        this.nursingHome = nursingHome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Senior senior)) return false;
        return Objects.equals(id, senior.id) && Objects.equals(firstName, senior.firstName) && Objects.equals(lastName, senior.lastName) && Objects.equals(nursingHome, senior.nursingHome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, nursingHome);
    }

    @Override
    public String toString() {
        return "Senior{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nursingHome='" + nursingHome + '\'' +
                '}';
    }
}
