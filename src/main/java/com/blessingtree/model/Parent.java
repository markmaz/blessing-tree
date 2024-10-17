package com.blessingtree.model;
import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="parents")
public class Parent {
    public Parent(){}

    public Parent(Long id, String firstName, String lastName, String primaryPhone, String secondaryPhone, List<Child> children) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.primaryPhone = primaryPhone;
        this.secondaryPhone = secondaryPhone;
        this.children = children;
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

    @Column(name="phone_2")
    private String secondaryPhone;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Child> children;

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

    public List<Child> getChildren() {
        return children;
    }

    public void setChildren(List<Child> children) {
        this.children = children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Parent parent)) return false;
        return Objects.equals(id, parent.id) && Objects.equals(firstName, parent.firstName) && Objects.equals(lastName, parent.lastName) && Objects.equals(primaryPhone, parent.primaryPhone) && Objects.equals(secondaryPhone, parent.secondaryPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, primaryPhone, secondaryPhone);
    }

    @Override
    public String toString() {
        return "Parent{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", primaryPhone='" + primaryPhone + '\'' +
                ", secondaryPhone='" + secondaryPhone + '\'' +
                ", children=" + children +
                '}';
    }
}
