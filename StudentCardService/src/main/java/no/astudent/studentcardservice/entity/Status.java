package no.astudent.studentcardservice.entity;

public enum Status {
    PENDING("PENDING"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED");

    private final String text;

    Status(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
