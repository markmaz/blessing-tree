package com.blessingtree.dto;

import java.util.Objects;

public class FamilyNoteDTO {
    private String note;

    private Long note_id;

    private String note_date;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getNote_id() {
        return note_id;
    }

    public void setNote_id(Long note_id) {
        this.note_id = note_id;
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
        if (!(o instanceof FamilyNoteDTO that)) return false;
        return Objects.equals(note, that.note) && Objects.equals(note_id, that.note_id) && Objects.equals(note_date, that.note_date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(note, note_id, note_date);
    }

    @Override
    public String toString() {
        return "FamilyNoteDTO{" +
                "note='" + note + '\'' +
                ", note_id=" + note_id +
                ", note_date='" + note_date + '\'' +
                '}';
    }
}
