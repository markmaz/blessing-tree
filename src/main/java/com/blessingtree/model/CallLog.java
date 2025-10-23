package com.blessingtree.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Where;

import java.util.Objects;

@Entity
@Table(name = "sponsor_call_log")
@Where(clause = "active = true")
public class CallLog {
    @Id
    @Column(name="sponsor_call_log_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sponsor_id", nullable = true)
    private Sponsor sponsor;

    @Column(name="call_date")
    private String callDate;

    @Column(name="call_description")
    private String callDescription;

    @Column(name="active")
    private Boolean active;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getCallDescription() {
        return callDescription;
    }

    public void setCallDescription(String callDescription) {
        this.callDescription = callDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CallLog callLog)) return false;
        return Objects.equals(id, callLog.id) && Objects.equals(sponsor, callLog.sponsor) && Objects.equals(callDate, callLog.callDate) && Objects.equals(callDescription, callLog.callDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sponsor, callDate, callDescription);
    }

    @Override
    public String toString() {
        return "CallLog{" +
                "id=" + id +
                ", sponsor=" + sponsor +
                ", callDate='" + callDate + '\'' +
                ", callDescription='" + callDescription + '\'' +
                '}';
    }
}
