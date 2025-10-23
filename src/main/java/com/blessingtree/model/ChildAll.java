package com.blessingtree.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name="children")
public class ChildAll extends AuditRecord{
    @Id
    @Column(name="child_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="parent_id", nullable = false)
    private ParentAll parent;

    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GiftAll> gifts;

    @Column(name="age")
    private String age;

    @Column(name="gender")
    private String gender;

    @Column(name="name")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParentAll getParent() {
        return parent;
    }

    public void setParent(ParentAll parent) {
        this.parent = parent;
    }

    public List<GiftAll> getGifts() {
        return gifts;
    }

    public void setGifts(List<GiftAll> gifts) {
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
        if (!(o instanceof ChildAll child)) return false;
        return Objects.equals(id, child.id) && Objects.equals(parent, child.parent) && Objects.equals(gifts, child.gifts) && Objects.equals(age, child.age) && Objects.equals(gender, child.gender) && Objects.equals(name, child.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parent, gifts, age, gender, name);
    }

    @Override
    public String toString() {
        return "Child{" +
                "id=" + id +
                ", parent=" + parent +
                ", gifts=" + gifts +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
