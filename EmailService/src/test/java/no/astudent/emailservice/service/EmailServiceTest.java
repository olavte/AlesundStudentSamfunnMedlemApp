package no.astudent.emailservice.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;

import no.astudent.emailservice.config.EmailConfig;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class EmailServiceTest {

    private EmailService emailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private EmailConfig emailConfig;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        emailService = new EmailService(javaMailSender, emailConfig);
    }

    @Test
    public void sendEmailTest() {

        boolean testSendNormalEmail = emailService.sendEmail("frevalde@gmail.com", "Unit test", "test");
        assertTrue(testSendNormalEmail);

        boolean testSendingWithEmptyVariables = emailService.sendEmail(null, null, null);
        assertFalse(testSendingWithEmptyVariables);

        boolean testSendingWithSomeEmptyVariables = emailService.sendEmail("tellnesoliver@gmail.com", null, null);
        assertTrue(testSendingWithSomeEmptyVariables);

        boolean testSendingWithSomeOtherEmptyVariables = emailService.sendEmail("example@example.org", "Hello friend! :D", null);
        assertTrue(testSendingWithSomeOtherEmptyVariables);

        boolean testSendingToNoEmail = emailService.sendEmail("notAnEmailAtAll", "This is subject", "This is content");
        assertFalse(testSendingToNoEmail);
    }

    @Test
    public void sendVerifyEmailTest() {

        boolean testNormalVerificationCode = emailService.sendVerifyEmail("vndisohj389rfi23oj", "Frank Even Valde", "frevalde@gmail.com");
        assertTrue(testNormalVerificationCode);

        boolean testWithNoEmail = emailService.sendVerifyEmail("vndisohj389rfi23oj", "Oliver", "");
        assertFalse(testWithNoEmail);

        boolean testWithFakeEmail = emailService.sendVerifyEmail("vndisohj389rfi23oj", "Olav", "fakeEmail");
        assertFalse(testWithFakeEmail);
    }

    @Test
    public void sendResetPasswordMailTest() {

        boolean testNormalVerificationCode = emailService.sendPasswordResetMail("vndisohj389rfi23oj", "Frank Even Valde", "frevalde@gmail.com");
        assertTrue(testNormalVerificationCode);

        boolean testWithNoEmail = emailService.sendPasswordResetMail("vndisohj389rfi23oj", "Oliver", "");
        assertFalse(testWithNoEmail);

        boolean testWithFakeEmail = emailService.sendPasswordResetMail("vndisohj389rfi23oj", "Olav", "fakeEmail");
        assertFalse(testWithFakeEmail);
    }
}
