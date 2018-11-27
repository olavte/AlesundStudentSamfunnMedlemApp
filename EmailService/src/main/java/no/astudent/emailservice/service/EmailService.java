package no.astudent.emailservice.service;

import no.astudent.emailservice.domain.Email;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import no.astudent.emailservice.config.EmailConfig;

@Service
public class EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailService.class);

    private final JavaMailSender emailSender;

    private final EmailConfig emailConfig;

    @Autowired
    public EmailService(JavaMailSender emailSender, EmailConfig emailConfig) {
        this.emailSender = emailSender;
        this.emailConfig = emailConfig;
    }

    public boolean sendEmail(String to, String subject, String content) {
        Email email = new Email("noreply@astudent.no", to, subject, content);

        if (!isEmailValid(email)) {
            LOGGER.info("Could not send email. Email had invalid from/to fields.");
            return false;
        }

        try {
            emailSender.send(email);
            return true;
        } catch (MailException e) {
            LOGGER.error("Failed to send email to({}) with subject({}), got exception({})", email.getTo(), email.getSubject(), e.toString());
            return false;
        }

    }

    public boolean sendVerifyEmail(String verificationCode, String name, String email) {
        String subject = "Verifiser ditt medlemskap hos Ålesund Studentsamfunn";
        StringBuilder content = new StringBuilder();
        content.append("Hei ").append(name).append(",\n\n");
        content.append("Trykk deg inn på lenken under for å fullføre din registrering hos Ålesund Studentsamfunn:\n\n");
        content.append(emailConfig.getUrl()).append("/verify?code=").append(verificationCode).append("&email=").append(email);
        content.append("\n\n\r\n\n");
        content.append("Mvh Ålesund Studentsamfunn\n");
        content.append("Larsgårdsvegen 2, 6009 Ålesund\n");
        content.append("70 16 16 30\n");
        content.append("support@astudent.no\n");

        if (!sendEmail(email, subject, content.toString())) {
            LOGGER.error("Could not send verification-email to member with email({})", email);
            return false;
        }
        return true;
    }

    public boolean sendPasswordResetMail(String verificationCode, String name, String email) {
        String subject = "Nytt passord for din medlemskonto hos Ålesund Studentsamfunn";
        StringBuilder content = new StringBuilder();
        content.append("Hei ").append(name).append(",\n\n");
        content.append("Trykk deg inn på lenken under for å sette ett nytt passord for din medlemskonto hos Ålesund Studentsamfunn:\n\n");
        content.append(emailConfig.getUrl()).append("/resetPassword?code=").append(verificationCode).append("&email=").append(email);
        content.append("\n\n\r\n\n");
        content.append("Mvh Ålesund Studentsamfunn\n");
        content.append("Larsgårdsvegen 2, 6009 Ålesund\n");
        content.append("70 16 16 30\n");
        content.append("support@astudent.no\n");

        if (!sendEmail(email, subject, content.toString())) {
            LOGGER.error("Could not send reset-password email to member with email({})", email);
            return false;
        }
        return true;
    }

    private boolean isEmailValid(Email email) {

        if (email == null || email.getTo()[0] == null || email.getFrom() == null) {
            LOGGER.error("Invalid email-address provided, email contained null values({})", email);
            return false;
        }

        if (!email.getFrom().contains("@") || !email.getFrom().contains(".")) {
            LOGGER.error("Invalid email-address provided in from-field({})", email.getFrom());
            return false;
        }

        for (String to : email.getTo()) {
            if (!to.contains("@") || !to.contains(".")) {
                LOGGER.error("Invalid email-address provided in to-field({})", to);
                return false;
            }
        }

        return true;
    }
}
