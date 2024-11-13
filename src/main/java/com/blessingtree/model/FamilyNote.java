package com.blessingtree.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "family_notes")
public class FamilyNote {
    @Id
    @Column(name = "family_note_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="parent_id", nullable = false)
    private Parent parent;

    @Column(name = "note")
    private String note;

    @Column(name = "note_date")
    private String note_date;

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote_date() {
        return note_date;
    }

    public void setNote_date(String note_date) {
        this.note_date = note_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FamilyNote that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(parent, that.parent) && Objects.equals(note, that.note) && Objects.equals(note_date, that.note_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parent, note, note_date);
    }

    @Override
    public String toString() {
        return "FamilyNote{" +
                "id=" + id +
                ", parent=" + parent +
                ", note='" + note + '\'' +
                ", note_date='" + note_date + '\'' +
                '}';
    }
}
