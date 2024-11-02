package com.blessingtree.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name="gifts")
public class Gift extends AuditRecord{
    public Gift(){}

    @Id
    @Column(name="gift_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="gift_description")
    private String description;

    @Column(name="size")
    private String size;

    @Column(name="status")
    private String status;

    @ManyToOne
    @JoinColumn(name="child_id")
    private Child child;

    @ManyToOne
    @JoinColumn(name="sponsor_id")
    private Sponsor sponsor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Gift gift)) return false;
        return Objects.equals(id, gift.id) && Objects.equals(description, gift.description) && Objects.equals(size, gift.size) && Objects.equals(status, gift.status) && Objects.equals(child, gift.child) && Objects.equals(sponsor, gift.sponsor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, size, status, child, sponsor);
    }

    @Override
    public String toString() {
        return "Gift{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", size='" + size + '\'' +
                ", status='" + status + '\'' +
                ", child=" + child +
                ", sponsor=" + sponsor +
                '}';
    }
}
