package com.blessingtree.dto;

import java.util.Objects;

public class FamilyNoteDTO {
    private String note;

    private Long id;

    private String noteDate;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FamilyNoteDTO that)) return false;
        return Objects.equals(note, that.note) && Objects.equals(id, that.id) && Objects.equals(noteDate, that.noteDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note, id, noteDate);
    }

    @Override
    public String toString() {
        return "FamilyNoteDTO{" +
                "note='" + note + '\'' +
                ", note_id=" + id +
                ", note_date='" + noteDate + '\'' +
                '}';
    }
}
