package com.blessingtree.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "family_notes")
public class FamilyNote {
    @Id
    @Column(name = "family_note_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="parent_id", nullable = false)
    private Parent parent;

    @Column(name = "note")
    private String note;

    @Column(name = "note_date")
    private String noteDate;

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

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String note_date) {
        this.noteDate = note_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FamilyNote that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(parent, that.parent) && Objects.equals(note, that.note) && Objects.equals(noteDate, that.noteDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, parent, note, noteDate);
    }

    @Override
    public String toString() {
        return "FamilyNote{" +
                "id=" + id +
                ", parent=" + parent +
                ", note='" + note + '\'' +
                ", note_date='" + noteDate + '\'' +
                '}';
    }
}
