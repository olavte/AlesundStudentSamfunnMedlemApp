package no.astudent.memberservice.entity;

import javax.persistence.*;

public enum AuthorityType {
    MEMBER("MEMBER"),
    ADMIN("ADMIN");

    private final String text;

    AuthorityType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
