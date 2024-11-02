package com.blessingtree.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="children")
public class Child extends AuditRecord{
    @Id
    @Column(name="child_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="parent_id", nullable = false)
    private Parent parent;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gift> gifts;

    @Column(name="age")
    private Long age;

    @Column(name="gender")
    private String gender;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public List<Gift> getGifts() {
        return gifts;
    }

    public void setGifts(List<Gift> gifts) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Child child)) return false;
        return Objects.equals(id, child.id) && Objects.equals(parent, child.parent) && Objects.equals(gifts, child.gifts) && Objects.equals(age, child.age) && Objects.equals(gender, child.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parent, gifts, age, gender);
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", parent=" + parent +
                ", gifts=" + gifts +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}
