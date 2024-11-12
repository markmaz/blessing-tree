package com.blessingtree.dto;

import java.util.Objects;

public class EmailDTO {
    private String subject;
    private String body;
    private String from;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmailDTO emailDTO)) return false;
        return Objects.equals(subject, emailDTO.subject) && Objects.equals(body, emailDTO.body) && Objects.equals(from, emailDTO.from);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, body, from);
    }

    @Override
    public String toString() {
        return "EmailDTO{" +
                "subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", from='" + from + '\'' +
                '}';
    }
}
