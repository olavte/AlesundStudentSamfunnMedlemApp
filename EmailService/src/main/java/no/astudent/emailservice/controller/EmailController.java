package no.astudent.emailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.astudent.emailservice.service.EmailService;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @RequestMapping("/sendMail")
    public boolean sendEmail(@RequestParam(value = "to", defaultValue = "") String to,
                             @RequestParam(value = "subject", defaultValue = "No subject") String subject,
                             @RequestParam(value = "content", defaultValue = "No content") String content) {
        return emailService.sendEmail(to, subject, content);
    }

    @RequestMapping("/sendVerification")
    public boolean sendVerification(@RequestParam(value = "hashcode") String hashcode,
                                    @RequestParam(value = "email") String email,
                                    @RequestParam(value = "name") String name) {
        return emailService.sendVerifyEmail(hashcode, name, email);
    }

    @RequestMapping("/sendPasswordReset")
    public boolean sendPasswordReset(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "hashcode") String hashcode,
                                     @RequestParam(value = "email") String email) {
        return emailService.sendPasswordResetMail(hashcode, name, email);
    }
}
