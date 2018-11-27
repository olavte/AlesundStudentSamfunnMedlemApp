package no.astudent.emailservice.domain;

import org.springframework.mail.SimpleMailMessage;

import java.util.Arrays;

public class Email extends SimpleMailMessage {

    public Email(String from, String to, String subject, String content) {
        this.setFrom(from);
        this.setTo(to);
        this.setSubject(subject);
        this.setText(content);
    }

    @Override
    public String toString() {
        return "To: " + Arrays.toString(getTo()) + ", From: " + getFrom()
            + ", Subject: " + getSubject() + ", Content: " + getText();
    }
}
