package no.astudent.memberservice.entity;

public enum Role {
    MEMBER("MEDLEM"),
    VOLUNTARY("FRIVILLIG"),
    HONORARY_MEMBER("ÆRESMEDLEM");

    private final String text;

    Role(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
